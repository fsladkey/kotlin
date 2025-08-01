/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.generators.tests

import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5
import org.jetbrains.kotlin.generators.model.annotation
import org.jetbrains.kotlin.konan.test.abi.AbstractNativeLibraryAbiReaderTest
import org.jetbrains.kotlin.konan.test.blackbox.AbstractNativeCodegenBoxTest
import org.jetbrains.kotlin.konan.test.blackbox.support.ClassLevelProperty
import org.jetbrains.kotlin.konan.test.blackbox.support.EnforcedProperty
import org.jetbrains.kotlin.konan.test.blackbox.support.KLIB_IR_INLINER
import org.jetbrains.kotlin.konan.test.blackbox.support.TestKind
import org.jetbrains.kotlin.konan.test.blackbox.support.group.ClassicPipeline
import org.jetbrains.kotlin.konan.test.blackbox.support.group.UseExtTestCaseGroupProvider
import org.jetbrains.kotlin.konan.test.blackbox.support.settings.CacheMode
import org.jetbrains.kotlin.konan.test.diagnostics.*
import org.jetbrains.kotlin.konan.test.evolution.AbstractNativeKlibEvolutionTest
import org.jetbrains.kotlin.konan.test.headerklib.*
import org.jetbrains.kotlin.konan.test.irText.*
import org.jetbrains.kotlin.konan.test.dump.*
import org.jetbrains.kotlin.konan.test.klib.AbstractKlibCrossCompilationIdentityTest
import org.jetbrains.kotlin.konan.test.klib.AbstractKlibCrossCompilationIdentityWithPreSerializationLoweringTest
import org.jetbrains.kotlin.konan.test.serialization.AbstractNativeIrDeserializationTest
import org.jetbrains.kotlin.konan.test.serialization.AbstractNativeIrDeserializationWithInlinedFunInKlibTest
import org.jetbrains.kotlin.konan.test.syntheticAccessors.*
import org.jetbrains.kotlin.test.TargetBackend
import org.jetbrains.kotlin.test.utils.CUSTOM_TEST_DATA_EXTENSION_PATTERN
import org.junit.jupiter.api.Tag

fun main() {
    System.setProperty("java.awt.headless", "true")
    val k1BoxTestDir = listOf("multiplatform/k1")

    generateTestGroupSuiteWithJUnit5 {
        // irText tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/ir/irText") {
            testClass<AbstractLightTreeNativeIrTextTest> {
                model()
            }
            testClass<AbstractPsiNativeIrTextTest> {
                model()
            }
        }

        // New frontend test infrastructure tests
        testGroup(testsRoot = "native/native.tests/klib-ir-inliner/tests-gen", testDataRoot = "compiler/testData/diagnostics") {
            testClass<AbstractPsiNativeDiagnosticsTest>(
                suiteTestClassName = "PsiOldFrontendNativeDiagnosticsTestGenerated",
            ) {
                model("nativeTests", excludedPattern = CUSTOM_TEST_DATA_EXTENSION_PATTERN)
            }

            testClass<AbstractLightTreeNativeDiagnosticsTest>(
                suiteTestClassName = "LightTreeOldFrontendNativeDiagnosticsTestGenerated",
            ) {
                model("nativeTests", excludedPattern = CUSTOM_TEST_DATA_EXTENSION_PATTERN)
            }

            testClass<AbstractPsiNativeDiagnosticsWithBackendTestBase>(
                suiteTestClassName = "PsiNativeKlibDiagnosticsTestGenerated",
                annotations = listOf(klib())
            ) {
                model("klibSerializationTests")
                // KT-67300: TODO: extract specialBackendChecks into own test runner, invoking Native backend facade at the end
                model("nativeTests/specialBackendChecks")
            }

            testClass<AbstractLightTreeNativeDiagnosticsWithBackendTestBase>(
                suiteTestClassName = "LightTreeNativeKlibDiagnosticsTestGenerated",
                annotations = listOf(klib())
            ) {
                model("klibSerializationTests")
                // KT-67300: TODO: extract specialBackendChecks into own test runner, invoking Native backend facade at the end
                model("nativeTests/specialBackendChecks")
            }

            testClass<AbstractNativeDiagnosticsWithBackendWithInlinedFunInKlibTestBase>(
                suiteTestClassName = "NativeKlibDiagnosticsWithInlinedFunInKlibTestGenerated",
                annotations = listOf(klib())
            ) {
                model("klibSerializationTests")
                // KT-67300: TODO: extract specialBackendChecks into own test runner, invoking Native backend facade at the end
                model("nativeTests/specialBackendChecks")
                model("testsWithAnyBackend")
            }
        }

        // Dump KLIB metadata tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/dump-metadata") {
            testClass<AbstractNativeKlibDumpMetadataTest> {
                model(pattern = "^([^_](.+)).kt$", recursive = true)
            }
        }

        // Dump KLIB IR tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/dump-ir") {
            testClass<AbstractNativeKlibDumpIrTest> {
                model(pattern = "^([^_](.+)).kt$", recursive = true)
            }
        }

        // Dump KLIB IR signatures tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/dump-signatures") {
            testClass<AbstractNativeKlibDumpIrSignaturesTest> {
                model(pattern = "^([^_](.+)).kt$", recursive = true)
            }
        }

        // Dump KLIB metadata signatures tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/dump-signatures") {
            testClass<AbstractNativeKlibDumpMetadataSignaturesTest> {
                model(pattern = "^([^_](.+)).(kt|def)$", recursive = true)
            }
        }

        // Header klib comparison tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/header-klibs/comparison") {
            testClass<AbstractNativeHeaderKlibComparisonTest> {
                model(extension = null, recursive = false)
            }
        }

        // Header klib compilation tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/header-klibs/compilation") {
            testClass<AbstractNativeHeaderKlibCompilationTest> {
                model(extension = null, recursive = false)
            }
        }

        // KLIB evolution tests.
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/klib/evolution") {
            testClass<AbstractNativeKlibEvolutionTest> {
                model(recursive = false)
            }
        }

        // Codegen/box tests for IR Inliner at 1st phase, invoked before K2 Klib Serializer
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/codegen") {
            testClass<AbstractNativeCodegenBoxTest>(
                suiteTestClassName = "NativeCodegenBoxWithInlinedFunInKlibTestGenerated",
                annotations = listOf(
                    klibIrInliner(),
                    provider<UseExtTestCaseGroupProvider>()
                )
            ) {
                model("box", targetBackend = TargetBackend.NATIVE, excludeDirs = k1BoxTestDir)
                model("boxInline", targetBackend = TargetBackend.NATIVE)
            }
            testClass<AbstractNativeIrDeserializationTest> {
                model("box", excludeDirs = k1BoxTestDir, nativeTestInNonNativeTestInfra = true)
                model("boxInline", nativeTestInNonNativeTestInfra = true)
            }
            testClass<AbstractNativeIrDeserializationWithInlinedFunInKlibTest> {
                model("box", excludeDirs = k1BoxTestDir, nativeTestInNonNativeTestInfra = true)
                model("boxInline", nativeTestInNonNativeTestInfra = true)
            }
        }

        // Codegen/box tests for synthetic accessor tests
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/klib/syntheticAccessors") {
            testClass<AbstractNativeCodegenBoxTest>(
                suiteTestClassName = "NativeKlibSyntheticAccessorsBoxTestGenerated",
                annotations = listOf(
                    klibIrInliner(),
                    provider<UseExtTestCaseGroupProvider>(),
                )
            ) {
                model(targetBackend = TargetBackend.NATIVE)
            }
        }

        // KLIB synthetic accessor tests.
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/klib/syntheticAccessors") {
            testClass<AbstractNativeKlibSyntheticAccessorTest>(
                annotations = listOf(
                    *klibSyntheticAccessors(),
                    klibIrInliner(),
                )
            ) {
                model()
            }
        }

        // KLIB cross-compilation tests.
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "native/native.tests/testData/klib/cross-compilation/identity") {
            testClass<AbstractKlibCrossCompilationIdentityTest> {
                model()
            }
            testClass<AbstractKlibCrossCompilationIdentityWithPreSerializationLoweringTest> {
                model()
            }
        }
    }

    generateTestGroupSuiteWithJUnit5 {
        testGroup("native/native.tests/klib-ir-inliner/tests-gen", "compiler/testData/klib/dump-abi/content") {
            testClass<AbstractNativeLibraryAbiReaderTest> {
                model()
            }
        }
    }
}

fun frontendClassic() = arrayOf(
    annotation(ClassicPipeline::class.java)
)

private fun klib() = annotation(Tag::class.java, "klib")
fun klibIrInliner() = annotation(Tag::class.java, KLIB_IR_INLINER)
private fun klibSyntheticAccessors() = arrayOf(
    annotation(
        EnforcedProperty::class.java,
        "property" to ClassLevelProperty.TEST_KIND,
        "propertyValue" to TestKind.STANDALONE.name
    ),
    annotation(
        EnforcedProperty::class.java,
        "property" to ClassLevelProperty.CACHE_MODE,
        "propertyValue" to CacheMode.Alias.NO.name
    ),
    provider<UseExtTestCaseGroupProvider>(),
)
