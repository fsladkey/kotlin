// IGNORE_FIR_DIAGNOSTICS
// RUN_PIPELINE_TILL: FIR2IR
// MODULE: m1-common
// FILE: common.kt
annotation class Ann

expect enum class E {
    @Ann
    FOO,
    MISSING_ON_ACTUAL
}

// MODULE: m1-jvm()()(m1-common)
// FILE: jvm.kt
actual enum class <!ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT, ACTUAL_WITHOUT_EXPECT!>E<!> {
    FOO
}

/* GENERATED_FIR_TAGS: actual, annotationDeclaration, enumDeclaration, enumEntry, expect */
