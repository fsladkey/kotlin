// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -NOTHING_TO_INLINE
// See KT-9143: smart cast on a variable nulled inside a lambda argument
inline fun <T> foo(t1: T, t2: T) = t1 ?: t2

inline fun <T> bar(l: (T) -> Unit): T = null!!

fun use() {
    var x: Int?
    x = 5
    // Write to x is AFTER
    <!DEBUG_INFO_SMARTCAST!>x<!>.hashCode()
    // No smart cast should be here!
    foo(bar { x = null }, <!SMARTCAST_IMPOSSIBLE!>x<!>.hashCode())
}

/* GENERATED_FIR_TAGS: assignment, checkNotNullCall, elvisExpression, functionDeclaration, functionalType, inline,
integerLiteral, lambdaLiteral, localProperty, nullableType, propertyDeclaration, smartcast, typeParameter */
