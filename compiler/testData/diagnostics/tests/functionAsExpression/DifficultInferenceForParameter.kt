// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_VARIABLE -UNUSED_PARAMETER

fun <T> foo(f : (T) -> T) : T = throw Exception()

fun test() {
    val a : Int = foo(fun (x) = x)
}

/* GENERATED_FIR_TAGS: anonymousFunction, functionDeclaration, functionalType, localProperty, nullableType,
propertyDeclaration, typeParameter */
