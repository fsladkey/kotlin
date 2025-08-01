// RUN_PIPELINE_TILL: FRONTEND
import kotlin.contracts.*

@Suppress("OPT_IN_USAGE_ERROR", "OPT_IN_USAGE_FUTURE_ERROR")
fun foo(f1: () -> Unit, f2: () -> Unit) {
    contract {
        callsInPlace(f1, InvocationKind.EXACTLY_ONCE)
        callsInPlace(f2, InvocationKind.EXACTLY_ONCE)
    }
    f2()
    f1()
}

fun test() {
    var s: String? = <!VARIABLE_WITH_REDUNDANT_INITIALIZER!>null<!>
    s = ""
    foo(
        { <!SMARTCAST_IMPOSSIBLE!>s<!>.length }, // unstable since lambda evaluation order is indeterministic
        { s = null },
    )
    s = ""
    foo(
        { s = null },
        { <!SMARTCAST_IMPOSSIBLE!>s<!>.length }, // unstable since lambda evaluation order is indeterministic
    )
    s = ""
    foo(
        { <!SMARTCAST_IMPOSSIBLE!>s<!>.length }, // stable
        { <!SMARTCAST_IMPOSSIBLE!>s<!>.length }, // stable
    )
    s = null
}

/* GENERATED_FIR_TAGS: assignment, contractCallsEffect, contracts, functionDeclaration, functionalType, lambdaLiteral,
localProperty, nullableType, propertyDeclaration, smartcast, stringLiteral */
