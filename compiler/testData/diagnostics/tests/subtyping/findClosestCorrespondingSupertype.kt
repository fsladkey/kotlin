// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_VARIABLE

interface X<T>
interface A: X<String>
interface B : <!INCONSISTENT_TYPE_PARAMETER_VALUES!>A, X<Int><!>

fun foo(x: B) {
    // Checks that when checking subtypes we search closes corresponding constructor (e.g. with BFS)
    val y: X<Int> = x
}

/* GENERATED_FIR_TAGS: functionDeclaration, interfaceDeclaration, localProperty, nullableType, propertyDeclaration,
typeParameter */
