// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE

interface Tr
interface G<T>

fun test(tr: Tr) {
    val v = tr as <!NO_TYPE_ARGUMENTS_ON_RHS!>G?<!>
    // If v is not nullable, there will be a warning on this line:
    checkSubtype<G<*>>(v!!)
}

/* GENERATED_FIR_TAGS: asExpression, checkNotNullCall, classDeclaration, funWithExtensionReceiver, functionDeclaration,
functionalType, infix, interfaceDeclaration, localProperty, nullableType, propertyDeclaration, starProjection,
typeParameter, typeWithExtension */
