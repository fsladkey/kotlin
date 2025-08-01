// RUN_PIPELINE_TILL: BACKEND
// FILE: A.java

class A {
    enum Empty {}

    static Empty foo() { return null; }
}

// FILE: 1.kt

fun bar() = when (A.foo()) {
    null -> "null"
    else -> "else"
}

fun <T : Number?> baz(t: T) = when (t) {
    is Int -> "int"
    is Long -> "long"
    null -> "null"
    else -> "else"
}

/* GENERATED_FIR_TAGS: equalityExpression, functionDeclaration, isExpression, nullableType, smartcast, stringLiteral,
typeConstraint, typeParameter, whenExpression, whenWithSubject */
