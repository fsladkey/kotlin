// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
import kotlin.contracts.*

@Suppress("OPT_IN_USAGE_ERROR", "OPT_IN_USAGE_FUTURE_ERROR")
fun exactlyOnce(f: () -> Unit) {
    contract {
        callsInPlace(f, InvocationKind.EXACTLY_ONCE)
    }
    f()
}

fun test() {
    var s: String? = ""
    if (s != null) {
        val block: () -> Unit
        exactlyOnce {
            block = { s = null }
        }
        block()
        <!SMARTCAST_IMPOSSIBLE!>s<!>.length
    }
}

/* GENERATED_FIR_TAGS: assignment, contractCallsEffect, contracts, equalityExpression, functionDeclaration,
functionalType, ifExpression, lambdaLiteral, localProperty, nullableType, propertyDeclaration, smartcast, stringLiteral */
