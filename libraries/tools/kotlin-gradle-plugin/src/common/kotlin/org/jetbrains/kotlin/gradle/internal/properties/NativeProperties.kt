/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.internal.properties

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.internal.properties.NativeProperties.Companion.KONAN_DATA_DIR
import org.jetbrains.kotlin.gradle.utils.NativeCompilerDownloader
import java.io.File
import java.net.URI
import java.nio.file.Paths

internal val Project.nativeProperties: NativeProperties
    get() = NativePropertiesLoader(this)

internal interface NativeProperties {
    val isUseXcodeMessageStyleEnabled: Provider<Boolean>
    val kotlinNativeVersion: Provider<String>
    val jvmArgs: Provider<List<String>>
    val forceDisableRunningInProcess: Provider<Boolean>
    val konanDataDir: Provider<File?>
    val downloadFromMaven: Provider<Boolean>
    val isToolchainEnabled: Provider<Boolean>

    /**
     * Location Kotlin/Native toolchain dependencies are downloaded from, see `kotlin.native.dependenciesUrl`.
     *
     * Always an absolute url when present, so that a file name can simply be appended to it.
     */
    val dependenciesUrl: Provider<String>

    /**
     * Value of 'kotlin.native.home' property.
     *
     * It may be empty.
     */
    val userProvidedNativeHome: Provider<String>

    /**
     * Actual Kotlin Native home directory calculated based on user configuration, host os and other inputs.
     *
     * Provider should always be present.
     */
    val actualNativeHomeDirectory: Provider<File>

    companion object {
        /**
         * Allows a user to provide a local Kotlin/Native distribution instead of a downloaded one.
         */
        internal val NATIVE_HOME = PropertiesBuildService.NullableStringGradleProperty(
            name = "kotlin.native.home"
        )

        /**
         * Allows the user to specify a custom location for the Kotlin/Native distribution.
         * This property takes precedence over the 'KONAN_DATA_DIR' environment variable.
         */
        internal val KONAN_DATA_DIR = PropertiesBuildService.NullableStringGradleProperty(
            name = "konan.data.dir"
        )
    }
}

private class NativePropertiesLoader(private val project: Project) : NativeProperties {

    private val propertiesService = project.propertiesService

    override val isUseXcodeMessageStyleEnabled: Provider<Boolean> = propertiesService.flatMap {
        it.property(USE_XCODE_MESSAGE_STYLE, project)
    }

    override val kotlinNativeVersion: Provider<String> = propertiesService.flatMap {
        it.propertyWithDeprecatedName(NATIVE_VERSION, NATIVE_VERSION_DEPRECATED, project)
            .orElse(NativeCompilerDownloader.DEFAULT_KONAN_VERSION)
    }

    override val jvmArgs: Provider<List<String>> = project.propertiesService.flatMap { propertiesService ->
        propertiesService.propertyWithDeprecatedName(NATIVE_JVM_ARGS, NATIVE_JVM_ARGS_DEPRECATED, project)
            .map {
                @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
                it!!.split("\\s+".toRegex())
            }
            .orElse(emptyList())
    }

    override val forceDisableRunningInProcess: Provider<Boolean> = propertiesService.flatMap {
        it.property(NATIVE_FORCE_DISABLE_IN_PROCESS, project)
    }

    private val konanDataDirProperty = propertiesService.flatMap { service ->
        service.property(KONAN_DATA_DIR, project).map { File(it) }
    }

    override val konanDataDir: Provider<File?> = konanDataDirProperty

    override val downloadFromMaven: Provider<Boolean> = propertiesService.flatMap {
        it.property(NATIVE_DOWNLOAD_FROM_MAVEN, project)
    }

    override val isToolchainEnabled: Provider<Boolean> = propertiesService.flatMap {
        it.property(NATIVE_TOOLCHAIN_ENABLED, project).zip(downloadFromMaven) { isToolchainEnabled, isDownloadFromMavenEnabled ->
            isToolchainEnabled && isDownloadFromMavenEnabled
        }
    }

    // Captured as a plain value: the resolver must not reference Project at execution time.
    private val rootDir = project.rootDir

    override val dependenciesUrl: Provider<String> = propertiesService.flatMap { service ->
        service.property(NATIVE_DEPENDENCIES_URL.name, project)
            .map { resolveNativeDependenciesUrl(it, rootDir) }
    }

    override val userProvidedNativeHome: Provider<String> = propertiesService.flatMap { service ->
        service.propertyWithDeprecatedName(NativeProperties.NATIVE_HOME, NATIVE_HOME_DEPRECATED, project)
    }

    override val actualNativeHomeDirectory: Provider<File> = konanDataDirProperty
        .map { NativeCompilerDownloader.getOsSpecificCompilerDirectory(project, it) }
        .orElse(
            userProvidedNativeHome
                .map { File(it) }
                .orElse(
                    project.providers.provider {
                        NativeCompilerDownloader.getDefaultCompilerDirectory(project)
                    }
                )
        )

    companion object {
        private const val PROPERTIES_PREFIX = "kotlin.native"

        /**
         * Forces K/N compiler to print messages which could be parsed by Xcode
         */
        private val USE_XCODE_MESSAGE_STYLE = PropertiesBuildService.NullableBooleanGradleProperty(
            name = "$PROPERTIES_PREFIX.useXcodeMessageStyle",
        )

        private val NATIVE_VERSION = PropertiesBuildService.NullableStringGradleProperty(
            name = "$PROPERTIES_PREFIX.version"
        )

        private val NATIVE_VERSION_DEPRECATED = PropertiesBuildService.NullableStringGradleProperty(
            name = "org.jetbrains.kotlin.native.version"
        )

        /**
         * Allows a user to specify additional arguments of a JVM executing a K/N compiler.
         */
        private val NATIVE_JVM_ARGS = PropertiesBuildService.NullableStringGradleProperty(
            name = "$PROPERTIES_PREFIX.jvmArgs",
        )

        private val NATIVE_JVM_ARGS_DEPRECATED = PropertiesBuildService.NullableStringGradleProperty(
            name = "org.jetbrains.kotlin.native.jvmArgs",
        )

        /**
         * Forces to run a compilation in a separate JVM.
         */
        private val NATIVE_FORCE_DISABLE_IN_PROCESS = PropertiesBuildService.BooleanGradleProperty(
            name = "$PROPERTIES_PREFIX.disableCompilerDaemon",
            defaultValue = false
        )

        private val NATIVE_HOME_DEPRECATED = PropertiesBuildService.NullableStringGradleProperty(
            name = "org.jetbrains.kotlin.native.home"
        )

        /**
         * Allows downloading Kotlin/Native distribution with maven.
         *
         * Makes downloader search for bundles in maven repositories specified in the project.
         */
        private val NATIVE_DOWNLOAD_FROM_MAVEN = PropertiesBuildService.BooleanGradleProperty(
            name = "$PROPERTIES_PREFIX.distribution.downloadFromMaven",
            defaultValue = true
        )

        /**
         * Enables kotlin native toolchain in native projects.
         */
        private val NATIVE_TOOLCHAIN_ENABLED = PropertiesBuildService.BooleanGradleProperty(
            name = "$PROPERTIES_PREFIX.toolchain.enabled",
            defaultValue = true
        )

        /**
         * Allows overriding the location Kotlin/Native toolchain dependencies are downloaded from.
         *
         * Accepts a remote url, a `file:` url or a plain filesystem path; relative paths are resolved
         * against the root project directory.
         */
        private val NATIVE_DEPENDENCIES_URL = PropertiesBuildService.NullableStringGradleProperty(
            name = "$PROPERTIES_PREFIX.dependenciesUrl"
        )

    }
}

/**
 * Matches a Windows path with a drive letter, such as `C:/konan` or `C:\konan`.
 *
 * Such a path parses as a [URI] whose scheme is the drive letter, so it has to be recognised before parsing.
 */
private val WINDOWS_DRIVE_PATH = Regex("^[a-zA-Z]:[/\\\\].*")

/** Length of `file:/`, the shortest possible `file:` url, which denotes the filesystem root. */
private const val FILE_URL_ROOT_LENGTH = 6

/**
 * Normalizes a user-supplied Kotlin/Native dependencies location into an absolute url.
 *
 * Accepts a remote url, a `file:` url or a plain filesystem path. Relative paths are resolved against [rootDir].
 *
 * @throws IllegalArgumentException for a `file:` url with an authority, such as `file://../konan`. Such a url
 *   names a host rather than a local path; left alone, it would silently resolve to the wrong directory.
 */
internal fun resolveNativeDependenciesUrl(rawValue: String, rootDir: File): String {
    val raw = rawValue.trim()
    // A drive-letter path is not a url, see [WINDOWS_DRIVE_PATH].
    val uri = if (WINDOWS_DRIVE_PATH.matches(raw)) null else runCatching { URI(raw) }.getOrNull()
    val scheme = uri?.scheme

    return when {
        // Remote locations are passed through untouched, `URL` knows how to open them.
        scheme != null && !scheme.equals("file", ignoreCase = true) -> raw
        // `file:relative/path` is opaque, its path lands in the scheme specific part.
        uri != null && uri.isOpaque -> rootDir.resolveToUrl(uri.schemeSpecificPart)
        uri?.authority != null -> throw IllegalArgumentException(
            "Unsupported value '$raw' for 'kotlin.native.dependenciesUrl': a 'file://' url must be followed by an " +
                    "absolute path, e.g. 'file:///opt/konan'. For a path relative to the root project, use a plain " +
                    "path such as '../prebuilts/konan'."
        )
        // A well-formed `file:/...` url. `Paths.get` applies platform rules, including drive letters.
        scheme != null -> runCatching { Paths.get(uri).toFile().toUrlString() }
            .getOrElse { rootDir.resolveToUrl(uri.path.orEmpty()) }
        // A plain filesystem path, absolute or relative.
        else -> rootDir.resolveToUrl(raw)
    }
}

private fun File.resolveToUrl(path: String): String = resolve(path).toUrlString()

private fun File.toUrlString(): String {
    val url = normalize().toURI().toString()
    // `File.toURI` appends a trailing slash for directories that already exist, which would make the result
    // depend on the state of the filesystem. Consumers append `/<file name>`, so drop it - but not for the
    // filesystem root, where the slash is the whole path.
    return if (url.length > FILE_URL_ROOT_LENGTH) url.trimEnd('/') else url
}
