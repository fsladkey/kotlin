// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
class A {
    @Deprecated("deprecated") companion object

    class B
}

val x1 = A.B()

/* GENERATED_FIR_TAGS: classDeclaration, companionObject, nestedClass, objectDeclaration, propertyDeclaration,
stringLiteral */
