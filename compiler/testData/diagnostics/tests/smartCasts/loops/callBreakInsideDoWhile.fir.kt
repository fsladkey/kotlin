// RUN_PIPELINE_TILL: FRONTEND
fun bar(): Boolean { return true }

fun gav(arg: Any): String { return if (arg is String) arg else "" }

public fun foo(x: String?): Int {
    var y: Any
    do {
        y = ""
        y = gav(if (x == null) break else x)
    } while (bar())
    y.hashCode()
    // x is null because of the break
    return x<!UNSAFE_CALL!>.<!>length
}

/* GENERATED_FIR_TAGS: assignment, break, doWhileLoop, equalityExpression, functionDeclaration, ifExpression,
isExpression, localProperty, nullableType, propertyDeclaration, smartcast, stringLiteral */
