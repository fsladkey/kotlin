// RUN_PIPELINE_TILL: BACKEND
//KT-2212 Incomplete nullability information
package kt2212

fun main() {
    val x: Int? = 1
    if (x == null) return
    System.out.println(<!DEBUG_INFO_SMARTCAST!>x<!>.plus(x<!UNNECESSARY_NOT_NULL_ASSERTION!>!!<!>))
}

/* GENERATED_FIR_TAGS: checkNotNullCall, equalityExpression, flexibleType, functionDeclaration, ifExpression,
integerLiteral, javaFunction, javaProperty, localProperty, nullableType, propertyDeclaration, smartcast */
