// RUN_PIPELINE_TILL: BACKEND
// DIAGNOSTICS: -UNUSED_PARAMETER
class My {
    constructor(arg: Int?) {
        run {
            var x = arg
            if (x == null) return@run
            <!DEBUG_INFO_SMARTCAST!>x<!>.hashCode()
        }   
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, equalityExpression, ifExpression, lambdaLiteral, localProperty, nullableType,
propertyDeclaration, secondaryConstructor, smartcast */
