// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
//KT-1156 Throwing exception on the right side of elvis operator marks code unreachable


fun foo(maybe: Int?) {
    val i : Int = maybe ?: throw RuntimeException("No value")
    System.out.println(i)
}

/* GENERATED_FIR_TAGS: elvisExpression, flexibleType, functionDeclaration, javaFunction, javaProperty, localProperty,
nullableType, propertyDeclaration, stringLiteral */
