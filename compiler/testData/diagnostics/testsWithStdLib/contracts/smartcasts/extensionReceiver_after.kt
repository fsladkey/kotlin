// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: +AllowContractsForCustomFunctions +UseReturnsEffect +ContractsOnCallsWithImplicitReceiver
// OPT_IN: kotlin.contracts.ExperimentalContracts
// DIAGNOSTICS: -INVISIBLE_REFERENCE -INVISIBLE_MEMBER
//
// ISSUE: KT-28672

import kotlin.contracts.*

fun CharSequence?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }

    return this == null || <!DEBUG_INFO_SMARTCAST!>this<!>.length == 0
}

fun smartcastOnReceiver(s: String?) {
    with(s) {
        if (isNullOrEmpty()) {
            <!UNSAFE_CALL!>length<!>
        }
        else {
            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>length<!>
        }
    }
}

fun mixedReceiver(s: String?) {
    if (!s.isNullOrEmpty()) {
        with(<!DEBUG_INFO_SMARTCAST!>s<!>) {
            length
        }
    } else {
        with(s) {
            <!UNSAFE_CALL!>length<!>
        }
    }
}

/* GENERATED_FIR_TAGS: contractConditionalEffect, contracts, disjunctionExpression, equalityExpression,
funWithExtensionReceiver, functionDeclaration, ifExpression, integerLiteral, lambdaLiteral, nullableType, smartcast,
thisExpression */
