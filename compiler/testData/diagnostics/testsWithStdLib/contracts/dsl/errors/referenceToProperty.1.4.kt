// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: +AllowContractsForCustomFunctions +UseReturnsEffect +AllowContractsForNonOverridableMembers
// OPT_IN: kotlin.contracts.ExperimentalContracts
// DIAGNOSTICS: -INVISIBLE_REFERENCE -INVISIBLE_MEMBER

import kotlin.contracts.*

class Foo(val x: Int?) {
    fun isXNull(): Boolean {
        contract {
            returns(false) implies (<!ERROR_IN_CONTRACT_DESCRIPTION!>x<!> != null)
        }
        return x != null
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, contractConditionalEffect, contracts, equalityExpression, functionDeclaration,
lambdaLiteral, nullableType, primaryConstructor, propertyDeclaration */
