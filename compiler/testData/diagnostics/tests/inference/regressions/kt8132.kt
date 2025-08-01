// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_ANONYMOUS_PARAMETER
// KT-8132 Can't omit lambda parameter types

fun <T> test(foo: List<T>): T {
    return if (true)
        throw IllegalStateException()
    else
        foo.reduce { left, right -> left } // error: inferred type T is not subtype Nothing
}

fun <S, T: S> Iterable<T>.reduce(operation: (S, T) -> S): S = throw Exception()

/* GENERATED_FIR_TAGS: funWithExtensionReceiver, functionDeclaration, functionalType, ifExpression, lambdaLiteral,
nullableType, typeConstraint, typeParameter */
