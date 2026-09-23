/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.unitTests

import org.jetbrains.kotlin.gradle.internal.properties.resolveNativeDependenciesUrl
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for the `kotlin.native.dependenciesUrl` value resolution.
 *
 * Assertions are written as invariants and equivalence classes rather than exact strings, so that they hold on
 * every host. Path semantics, in particular for Windows drive letters, are delegated to the JDK and therefore
 * differ per platform.
 */
class NativeDependenciesUrlTest {

    private val rootDir = File("/projects/demo")

    private fun resolve(rawValue: String) = resolveNativeDependenciesUrl(rawValue, rootDir)

    @Test
    fun remoteUrlsArePassedThroughUnchanged() {
        val https = "https://download.jetbrains.com/kotlin/native"
        assertEquals(https, resolve(https))
        assertEquals("http://example.com/konan", resolve("http://example.com/konan"))
    }

    @Test
    fun nonFileSchemesAreNotTreatedAsPaths() {
        // These used to fall through to path resolution and silently become directories under the root project.
        assertEquals("ftp://example.com/konan", resolve("ftp://example.com/konan"))
    }

    @Test
    fun equivalentAbsoluteFormsAgree() {
        val expected = resolve("/opt/konan")
        assertEquals(expected, resolve("file:/opt/konan"))
        assertEquals(expected, resolve("file:///opt/konan"))
    }

    @Test
    fun equivalentRelativeFormsAgree() {
        val expected = resolve("../prebuilts/konan")
        assertEquals(expected, resolve("file:../prebuilts/konan"))
        // Not a valid url, but the only way to express a relative path with a scheme.
        assertEquals(expected, resolve("file://../prebuilts/konan"))
    }

    @Test
    fun relativePathsAreResolvedAgainstRootDirAndNormalized() {
        val url = resolve("../prebuilts/konan")
        assertTrue(url.startsWith("file:/"), url)
        assertTrue(url.endsWith("/projects/prebuilts/konan"), url)
        assertFalse(url.contains(".."), url)
    }

    @Test
    fun windowsDriveLetterIsNotDuplicated() {
        // `file:/C:/konan` yields the path `/C:/konan`, which naive handling turns into `C:\C:\konan`.
        for (rawValue in listOf("C:/konan", "file:/C:/konan", "file:///C:/konan", "file://C:/konan")) {
            val url = resolve(rawValue)
            assertFalse(url.contains("C:/C:"), "$rawValue produced $url")
            assertTrue(url.endsWith("/konan"), "$rawValue produced $url")
        }
    }

    @Test
    fun percentEncodingIsRoundTripped() {
        val url = resolve("file:/opt/with%20space/konan")
        assertTrue(url.endsWith("/with%20space/konan"), url)
    }

    @Test
    fun trailingSlashIsRemoved() {
        assertFalse(resolve("/opt/konan/").endsWith("/"))
    }

    @Test
    fun surroundingWhitespaceIsIgnored() {
        assertEquals(resolve("/opt/konan"), resolve("  /opt/konan  "))
    }
}
