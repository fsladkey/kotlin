/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.incremental;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.junit.jupiter.api.Tag;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateJsTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("js/js.translator/testData/incremental/invalidation")
@TestDataPath("$PROJECT_ROOT")
@Tag("legacy-frontend")
@Tag("es6")
public class JsIrES6InvalidationPerModuleTestGenerated extends AbstractJsIrES6InvalidationPerModuleTest {
  @Test
  @TestMetadata("abstractClassWithJsExport")
  public void testAbstractClassWithJsExport() {
    runTest("js/js.translator/testData/incremental/invalidation/abstractClassWithJsExport/");
  }

  @Test
  @TestMetadata("addJsFunCall")
  public void testAddJsFunCall() {
    runTest("js/js.translator/testData/incremental/invalidation/addJsFunCall/");
  }

  @Test
  @TestMetadata("addUpdateRemoveDependentFile")
  public void testAddUpdateRemoveDependentFile() {
    runTest("js/js.translator/testData/incremental/invalidation/addUpdateRemoveDependentFile/");
  }

  @Test
  @TestMetadata("addUpdateRemoveDependentModule")
  public void testAddUpdateRemoveDependentModule() {
    runTest("js/js.translator/testData/incremental/invalidation/addUpdateRemoveDependentModule/");
  }

  @Test
  public void testAllFilesPresentInInvalidation() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("js/js.translator/testData/incremental/invalidation"), Pattern.compile("^([^_](.+))$"), null, TargetBackend.JS_IR_ES6, false);
  }

  @Test
  @TestMetadata("associatedObjectKey")
  public void testAssociatedObjectKey() {
    runTest("js/js.translator/testData/incremental/invalidation/associatedObjectKey/");
  }

  @Test
  @TestMetadata("circleExportsUpdate")
  public void testCircleExportsUpdate() {
    runTest("js/js.translator/testData/incremental/invalidation/circleExportsUpdate/");
  }

  @Test
  @TestMetadata("circleInlineImportsUpdate")
  public void testCircleInlineImportsUpdate() {
    runTest("js/js.translator/testData/incremental/invalidation/circleInlineImportsUpdate/");
  }

  @Test
  @TestMetadata("class")
  public void testClass() {
    runTest("js/js.translator/testData/incremental/invalidation/class/");
  }

  @Test
  @TestMetadata("classFunctionsAndFields")
  public void testClassFunctionsAndFields() {
    runTest("js/js.translator/testData/incremental/invalidation/classFunctionsAndFields/");
  }

  @Test
  @TestMetadata("classWithJsExport")
  public void testClassWithJsExport() {
    runTest("js/js.translator/testData/incremental/invalidation/classWithJsExport/");
  }

  @Test
  @TestMetadata("companionConstVal")
  public void testCompanionConstVal() {
    runTest("js/js.translator/testData/incremental/invalidation/companionConstVal/");
  }

  @Test
  @TestMetadata("companionFunction")
  public void testCompanionFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/companionFunction/");
  }

  @Test
  @TestMetadata("companionInlineFunction")
  public void testCompanionInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/companionInlineFunction/");
  }

  @Test
  @TestMetadata("companionProperties")
  public void testCompanionProperties() {
    runTest("js/js.translator/testData/incremental/invalidation/companionProperties/");
  }

  @Test
  @TestMetadata("companionWithStdLibCall")
  public void testCompanionWithStdLibCall() {
    runTest("js/js.translator/testData/incremental/invalidation/companionWithStdLibCall/");
  }

  @Test
  @TestMetadata("constVals")
  public void testConstVals() {
    runTest("js/js.translator/testData/incremental/invalidation/constVals/");
  }

  @Test
  @TestMetadata("crossModuleModifyClassAncestors")
  public void testCrossModuleModifyClassAncestors() {
    runTest("js/js.translator/testData/incremental/invalidation/crossModuleModifyClassAncestors/");
  }

  @Test
  @TestMetadata("crossModuleObjectInitialization")
  public void testCrossModuleObjectInitialization() {
    runTest("js/js.translator/testData/incremental/invalidation/crossModuleObjectInitialization/");
  }

  @Test
  @TestMetadata("crossModuleReferences")
  public void testCrossModuleReferences() {
    runTest("js/js.translator/testData/incremental/invalidation/crossModuleReferences/");
  }

  @Test
  @TestMetadata("defaultParamMethod")
  public void testDefaultParamMethod() {
    runTest("js/js.translator/testData/incremental/invalidation/defaultParamMethod/");
  }

  @Test
  @TestMetadata("eagerInitialization")
  public void testEagerInitialization() {
    runTest("js/js.translator/testData/incremental/invalidation/eagerInitialization/");
  }

  @Test
  @TestMetadata("enum")
  public void testEnum() {
    runTest("js/js.translator/testData/incremental/invalidation/enum/");
  }

  @Test
  @TestMetadata("enumEntriesInlining")
  public void testEnumEntriesInlining() {
    runTest("js/js.translator/testData/incremental/invalidation/enumEntriesInlining/");
  }

  @Test
  @TestMetadata("enumsInInlineFunctions")
  public void testEnumsInInlineFunctions() {
    runTest("js/js.translator/testData/incremental/invalidation/enumsInInlineFunctions/");
  }

  @Test
  @TestMetadata("exceptionsFromInlineFunction")
  public void testExceptionsFromInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/exceptionsFromInlineFunction/");
  }

  @Test
  @TestMetadata("exportsThroughInlineFunction")
  public void testExportsThroughInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/exportsThroughInlineFunction/");
  }

  @Test
  @TestMetadata("externalOverriddenProperty")
  public void testExternalOverriddenProperty() {
    runTest("js/js.translator/testData/incremental/invalidation/externalOverriddenProperty/");
  }

  @Test
  @TestMetadata("fakeOverrideClassFunctionQualifiers")
  public void testFakeOverrideClassFunctionQualifiers() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideClassFunctionQualifiers/");
  }

  @Test
  @TestMetadata("fakeOverrideInheritance")
  public void testFakeOverrideInheritance() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideInheritance/");
  }

  @Test
  @TestMetadata("fakeOverrideInlineExtension")
  public void testFakeOverrideInlineExtension() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideInlineExtension/");
  }

  @Test
  @TestMetadata("fakeOverrideInlineFunction")
  public void testFakeOverrideInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideInlineFunction/");
  }

  @Test
  @TestMetadata("fakeOverrideInlineProperty")
  public void testFakeOverrideInlineProperty() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideInlineProperty/");
  }

  @Test
  @TestMetadata("fakeOverrideInterfaceFunctionQualifiers")
  public void testFakeOverrideInterfaceFunctionQualifiers() {
    runTest("js/js.translator/testData/incremental/invalidation/fakeOverrideInterfaceFunctionQualifiers/");
  }

  @Test
  @TestMetadata("fastPath1")
  public void testFastPath1() {
    runTest("js/js.translator/testData/incremental/invalidation/fastPath1/");
  }

  @Test
  @TestMetadata("fastPath2")
  public void testFastPath2() {
    runTest("js/js.translator/testData/incremental/invalidation/fastPath2/");
  }

  @Test
  @TestMetadata("fileNameClash")
  public void testFileNameClash() {
    runTest("js/js.translator/testData/incremental/invalidation/fileNameClash/");
  }

  @Test
  @TestMetadata("friendDependency")
  public void testFriendDependency() {
    runTest("js/js.translator/testData/incremental/invalidation/friendDependency/");
  }

  @Test
  @TestMetadata("functionDefaultParams")
  public void testFunctionDefaultParams() {
    runTest("js/js.translator/testData/incremental/invalidation/functionDefaultParams/");
  }

  @Test
  @TestMetadata("functionSignature")
  public void testFunctionSignature() {
    runTest("js/js.translator/testData/incremental/invalidation/functionSignature/");
  }

  @Test
  @TestMetadata("functionTypeInterface")
  public void testFunctionTypeInterface() {
    runTest("js/js.translator/testData/incremental/invalidation/functionTypeInterface/");
  }

  @Test
  @TestMetadata("functionTypeInterfaceReflect")
  public void testFunctionTypeInterfaceReflect() {
    runTest("js/js.translator/testData/incremental/invalidation/functionTypeInterfaceReflect/");
  }

  @Test
  @TestMetadata("genericFunctions")
  public void testGenericFunctions() {
    runTest("js/js.translator/testData/incremental/invalidation/genericFunctions/");
  }

  @Test
  @TestMetadata("genericInlineFunctions")
  public void testGenericInlineFunctions() {
    runTest("js/js.translator/testData/incremental/invalidation/genericInlineFunctions/");
  }

  @Test
  @TestMetadata("gettersAndSettersInlining")
  public void testGettersAndSettersInlining() {
    runTest("js/js.translator/testData/incremental/invalidation/gettersAndSettersInlining/");
  }

  @Test
  @TestMetadata("inlineBecomeNonInline")
  public void testInlineBecomeNonInline() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineBecomeNonInline/");
  }

  @Test
  @TestMetadata("inlineFunctionAnnotations")
  public void testInlineFunctionAnnotations() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineFunctionAnnotations/");
  }

  @Test
  @TestMetadata("inlineFunctionAsFunctionReference")
  public void testInlineFunctionAsFunctionReference() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineFunctionAsFunctionReference/");
  }

  @Test
  @TestMetadata("inlineFunctionAsParam")
  public void testInlineFunctionAsParam() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineFunctionAsParam/");
  }

  @Test
  @TestMetadata("inlineFunctionDefaultParams")
  public void testInlineFunctionDefaultParams() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineFunctionDefaultParams/");
  }

  @Test
  @TestMetadata("inlineFunctionWithObject")
  public void testInlineFunctionWithObject() {
    runTest("js/js.translator/testData/incremental/invalidation/inlineFunctionWithObject/");
  }

  @Test
  @TestMetadata("interfaceOpenMethods")
  public void testInterfaceOpenMethods() {
    runTest("js/js.translator/testData/incremental/invalidation/interfaceOpenMethods/");
  }

  @Test
  @TestMetadata("interfaceOpenMethodsInOpenClass")
  public void testInterfaceOpenMethodsInOpenClass() {
    runTest("js/js.translator/testData/incremental/invalidation/interfaceOpenMethodsInOpenClass/");
  }

  @Test
  @TestMetadata("interfaceSuperUsage")
  public void testInterfaceSuperUsage() {
    runTest("js/js.translator/testData/incremental/invalidation/interfaceSuperUsage/");
  }

  @Test
  @TestMetadata("interfaceWithDefaultParams")
  public void testInterfaceWithDefaultParams() {
    runTest("js/js.translator/testData/incremental/invalidation/interfaceWithDefaultParams/");
  }

  @Test
  @TestMetadata("interfaceWithJsExport")
  public void testInterfaceWithJsExport() {
    runTest("js/js.translator/testData/incremental/invalidation/interfaceWithJsExport/");
  }

  @Test
  @TestMetadata("jsCode")
  public void testJsCode() {
    runTest("js/js.translator/testData/incremental/invalidation/jsCode/");
  }

  @Test
  @TestMetadata("jsCodeWithConstString")
  public void testJsCodeWithConstString() {
    runTest("js/js.translator/testData/incremental/invalidation/jsCodeWithConstString/");
  }

  @Test
  @TestMetadata("jsCodeWithConstStringFromOtherModule")
  public void testJsCodeWithConstStringFromOtherModule() {
    runTest("js/js.translator/testData/incremental/invalidation/jsCodeWithConstStringFromOtherModule/");
  }

  @Test
  @TestMetadata("jsExport")
  public void testJsExport() {
    runTest("js/js.translator/testData/incremental/invalidation/jsExport/");
  }

  @Test
  @TestMetadata("jsExportReexport")
  public void testJsExportReexport() {
    runTest("js/js.translator/testData/incremental/invalidation/jsExportReexport/");
  }

  @Test
  @TestMetadata("jsExportWithClass")
  public void testJsExportWithClass() {
    runTest("js/js.translator/testData/incremental/invalidation/jsExportWithClass/");
  }

  @Test
  @TestMetadata("jsExportWithMultipleFiles")
  public void testJsExportWithMultipleFiles() {
    runTest("js/js.translator/testData/incremental/invalidation/jsExportWithMultipleFiles/");
  }

  @Test
  @TestMetadata("jsModuleAnnotation")
  public void testJsModuleAnnotation() {
    runTest("js/js.translator/testData/incremental/invalidation/jsModuleAnnotation/");
  }

  @Test
  @TestMetadata("jsModuleAnnotationOnObjectWithUsage")
  public void testJsModuleAnnotationOnObjectWithUsage() {
    runTest("js/js.translator/testData/incremental/invalidation/jsModuleAnnotationOnObjectWithUsage/");
  }

  @Test
  @TestMetadata("jsName")
  public void testJsName() {
    runTest("js/js.translator/testData/incremental/invalidation/jsName/");
  }

  @Test
  @TestMetadata("kotlinTest")
  public void testKotlinTest() {
    runTest("js/js.translator/testData/incremental/invalidation/kotlinTest/");
  }

  @Test
  @TestMetadata("languageVersionSettings")
  public void testLanguageVersionSettings() {
    runTest("js/js.translator/testData/incremental/invalidation/languageVersionSettings/");
  }

  @Test
  @TestMetadata("localInlineFunction")
  public void testLocalInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/localInlineFunction/");
  }

  @Test
  @TestMetadata("localObjectsLeakThroughInterface")
  public void testLocalObjectsLeakThroughInterface() {
    runTest("js/js.translator/testData/incremental/invalidation/localObjectsLeakThroughInterface/");
  }

  @Test
  @TestMetadata("mainFunction")
  public void testMainFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/mainFunction/");
  }

  @Test
  @TestMetadata("mainModuleInvalidation")
  public void testMainModuleInvalidation() {
    runTest("js/js.translator/testData/incremental/invalidation/mainModuleInvalidation/");
  }

  @Test
  @TestMetadata("modifyClassAncestors")
  public void testModifyClassAncestors() {
    runTest("js/js.translator/testData/incremental/invalidation/modifyClassAncestors/");
  }

  @Test
  @TestMetadata("moveAndModifyInlineFunction")
  public void testMoveAndModifyInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/moveAndModifyInlineFunction/");
  }

  @Test
  @TestMetadata("moveExternalDeclarationsBetweenFiles")
  public void testMoveExternalDeclarationsBetweenFiles() {
    runTest("js/js.translator/testData/incremental/invalidation/moveExternalDeclarationsBetweenFiles/");
  }

  @Test
  @TestMetadata("moveExternalDeclarationsBetweenJsModules")
  public void testMoveExternalDeclarationsBetweenJsModules() {
    runTest("js/js.translator/testData/incremental/invalidation/moveExternalDeclarationsBetweenJsModules/");
  }

  @Test
  @TestMetadata("moveFilesBetweenModules")
  public void testMoveFilesBetweenModules() {
    runTest("js/js.translator/testData/incremental/invalidation/moveFilesBetweenModules/");
  }

  @Test
  @TestMetadata("moveInlineFunctionBetweenModules")
  public void testMoveInlineFunctionBetweenModules() {
    runTest("js/js.translator/testData/incremental/invalidation/moveInlineFunctionBetweenModules/");
  }

  @Test
  @TestMetadata("multiModuleEagerInitialization")
  public void testMultiModuleEagerInitialization() {
    runTest("js/js.translator/testData/incremental/invalidation/multiModuleEagerInitialization/");
  }

  @Test
  @TestMetadata("multiPlatformClashFileNames")
  public void testMultiPlatformClashFileNames() {
    runTest("js/js.translator/testData/incremental/invalidation/multiPlatformClashFileNames/");
  }

  @Test
  @TestMetadata("multiPlatformSimple")
  public void testMultiPlatformSimple() {
    runTest("js/js.translator/testData/incremental/invalidation/multiPlatformSimple/");
  }

  @Test
  @TestMetadata("nestedClass")
  public void testNestedClass() {
    runTest("js/js.translator/testData/incremental/invalidation/nestedClass/");
  }

  @Test
  @TestMetadata("nonInlineBecomeInline")
  public void testNonInlineBecomeInline() {
    runTest("js/js.translator/testData/incremental/invalidation/nonInlineBecomeInline/");
  }

  @Test
  @TestMetadata("openClassWithInternalField")
  public void testOpenClassWithInternalField() {
    runTest("js/js.translator/testData/incremental/invalidation/openClassWithInternalField/");
  }

  @Test
  @TestMetadata("privateDeclarationLeakThroughDefaultParam")
  public void testPrivateDeclarationLeakThroughDefaultParam() {
    runTest("js/js.translator/testData/incremental/invalidation/privateDeclarationLeakThroughDefaultParam/");
  }

  @Test
  @TestMetadata("privateInlineFunction1")
  public void testPrivateInlineFunction1() {
    runTest("js/js.translator/testData/incremental/invalidation/privateInlineFunction1/");
  }

  @Test
  @TestMetadata("privateObjectsLeakThroughSealedInterface")
  public void testPrivateObjectsLeakThroughSealedInterface() {
    runTest("js/js.translator/testData/incremental/invalidation/privateObjectsLeakThroughSealedInterface/");
  }

  @Test
  @TestMetadata("removeFile")
  public void testRemoveFile() {
    runTest("js/js.translator/testData/incremental/invalidation/removeFile/");
  }

  @Test
  @TestMetadata("removeModule")
  public void testRemoveModule() {
    runTest("js/js.translator/testData/incremental/invalidation/removeModule/");
  }

  @Test
  @TestMetadata("removeUnusedFile")
  public void testRemoveUnusedFile() {
    runTest("js/js.translator/testData/incremental/invalidation/removeUnusedFile/");
  }

  @Test
  @TestMetadata("renameFile")
  public void testRenameFile() {
    runTest("js/js.translator/testData/incremental/invalidation/renameFile/");
  }

  @Test
  @TestMetadata("renameModule")
  public void testRenameModule() {
    runTest("js/js.translator/testData/incremental/invalidation/renameModule/");
  }

  @Test
  @TestMetadata("simple")
  public void testSimple() {
    runTest("js/js.translator/testData/incremental/invalidation/simple/");
  }

  @Test
  @TestMetadata("splitJoinModule")
  public void testSplitJoinModule() {
    runTest("js/js.translator/testData/incremental/invalidation/splitJoinModule/");
  }

  @Test
  @TestMetadata("suspendFunctions")
  public void testSuspendFunctions() {
    runTest("js/js.translator/testData/incremental/invalidation/suspendFunctions/");
  }

  @Test
  @TestMetadata("suspendGenerator")
  public void testSuspendGenerator() {
    runTest("js/js.translator/testData/incremental/invalidation/suspendGenerator/");
  }

  @Test
  @TestMetadata("suspendInterfaceWithDefaultParams")
  public void testSuspendInterfaceWithDefaultParams() {
    runTest("js/js.translator/testData/incremental/invalidation/suspendInterfaceWithDefaultParams/");
  }

  @Test
  @TestMetadata("toplevelProperties")
  public void testToplevelProperties() {
    runTest("js/js.translator/testData/incremental/invalidation/toplevelProperties/");
  }

  @Test
  @TestMetadata("transitiveInlineFunction")
  public void testTransitiveInlineFunction() {
    runTest("js/js.translator/testData/incremental/invalidation/transitiveInlineFunction/");
  }

  @Test
  @TestMetadata("typeScriptExportsPerFile")
  public void testTypeScriptExportsPerFile() {
    runTest("js/js.translator/testData/incremental/invalidation/typeScriptExportsPerFile/");
  }

  @Test
  @TestMetadata("typeScriptExportsPerModule")
  public void testTypeScriptExportsPerModule() {
    runTest("js/js.translator/testData/incremental/invalidation/typeScriptExportsPerModule/");
  }

  @Test
  @TestMetadata("unicodeSerializationAndDeserialization")
  public void testUnicodeSerializationAndDeserialization() {
    runTest("js/js.translator/testData/incremental/invalidation/unicodeSerializationAndDeserialization/");
  }

  @Test
  @TestMetadata("unstableIrReproducer")
  public void testUnstableIrReproducer() {
    runTest("js/js.translator/testData/incremental/invalidation/unstableIrReproducer/");
  }

  @Test
  @TestMetadata("updateExports")
  public void testUpdateExports() {
    runTest("js/js.translator/testData/incremental/invalidation/updateExports/");
  }

  @Test
  @TestMetadata("updateExportsAndInlineImports")
  public void testUpdateExportsAndInlineImports() {
    runTest("js/js.translator/testData/incremental/invalidation/updateExportsAndInlineImports/");
  }

  @Test
  @TestMetadata("variance")
  public void testVariance() {
    runTest("js/js.translator/testData/incremental/invalidation/variance/");
  }
}
