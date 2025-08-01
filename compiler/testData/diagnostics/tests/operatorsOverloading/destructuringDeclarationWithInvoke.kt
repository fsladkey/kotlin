// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-59715

class FunctionComponent {
    val component1: () -> String = { "hello" }
}

class I {
    operator fun invoke(): String = "hello"
}

class InvokeComponent {
    val component1: I = I()
}

fun test_1(c: FunctionComponent) {
    val (<!PROPERTY_AS_OPERATOR!>x<!>) = FunctionComponent()
    val (<!PROPERTY_AS_OPERATOR!>y<!>) = c
}

fun test_2(c: InvokeComponent) {
    val (<!PROPERTY_AS_OPERATOR!>x<!>) = FunctionComponent()
    val (<!PROPERTY_AS_OPERATOR!>y<!>) = c
}

/* GENERATED_FIR_TAGS: classDeclaration, destructuringDeclaration, functionDeclaration, functionalType, lambdaLiteral,
localProperty, operator, propertyDeclaration, stringLiteral */
