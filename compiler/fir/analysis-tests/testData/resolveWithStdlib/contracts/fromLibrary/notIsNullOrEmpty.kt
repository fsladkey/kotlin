// RUN_PIPELINE_TILL: FRONTEND
fun test_1(s: String?) {
    when {
        !s.isNullOrEmpty() -> s.length // Should be OK
    }
}

fun test_2(s: String?) {
    // contracts related
    if (s.isNullOrEmpty()) {
        s<!UNSAFE_CALL!>.<!>length // Should be bad
    } else {
        s.length // Should be OK
    }
}

/* GENERATED_FIR_TAGS: functionDeclaration, ifExpression, nullableType, smartcast, whenExpression */
