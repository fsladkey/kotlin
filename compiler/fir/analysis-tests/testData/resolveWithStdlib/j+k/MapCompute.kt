// RUN_PIPELINE_TILL: BACKEND
// FULL_JDK
// STDLIB_JDK8
// JVM_TARGET: 1.8

fun <D> MutableMap<String, MutableSet<D>>.initAndAdd(key: String, value: D) {
    this.compute(key) { _, maybeValues ->
        val setOfValues = maybeValues ?: mutableSetOf()
        setOfValues.add(value)
        setOfValues
    }
}

/* GENERATED_FIR_TAGS: elvisExpression, funWithExtensionReceiver, functionDeclaration, inProjection, lambdaLiteral,
localProperty, nullableType, outProjection, propertyDeclaration, samConversion, thisExpression, typeParameter */
