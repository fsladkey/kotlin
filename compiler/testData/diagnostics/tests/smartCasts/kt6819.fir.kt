// RUN_PIPELINE_TILL: FRONTEND
public fun test(o: String?): Boolean {
    return when {
        // Data flow info should propagate from o == null to o.length
        o == null<!COMMA_IN_WHEN_CONDITION_WITHOUT_ARGUMENT!>,<!> o.length == 0 -> false
        else -> true
    }
}

/* GENERATED_FIR_TAGS: disjunctionExpression, equalityExpression, functionDeclaration, integerLiteral, nullableType,
smartcast, whenExpression */
