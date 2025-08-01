// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
package test

annotation class Ann(
        val b1: Float,
        val b2: Float
)

@Ann(1.toFloat(), 1.0.toFloat()) class MyClass

// EXPECTED: @Ann(b1 = 1.0.toFloat(), b2 = 1.0.toFloat())

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, integerLiteral, primaryConstructor, propertyDeclaration */
