// RUN_PIPELINE_TILL: BACKEND
class Foo {
    operator fun invoke() {}
}

fun foo() {
    val x = Foo()

    x()
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, localProperty, operator, propertyDeclaration */
