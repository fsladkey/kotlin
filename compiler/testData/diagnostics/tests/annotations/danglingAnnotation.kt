// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
annotation class Anno(val position: String)

interface OriginalInterface {
    companion object {
        private const val prop = 0
    }

    @Anno("dangling $prop")<!SYNTAX!><!>
}

/* GENERATED_FIR_TAGS: annotationDeclaration, companionObject, const, integerLiteral, interfaceDeclaration,
objectDeclaration, primaryConstructor, propertyDeclaration, stringLiteral */
