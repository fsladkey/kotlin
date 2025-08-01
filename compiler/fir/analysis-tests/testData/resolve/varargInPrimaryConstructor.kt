// RUN_PIPELINE_TILL: BACKEND
class Foo(vararg val strings: String)

fun test_1(foo: Foo) {
    for (s in foo.strings) {
        s.length
    }
}

fun test_2(vararg strings: String) {
    for (s in strings) {
        s.length
    }
}

/* GENERATED_FIR_TAGS: capturedType, classDeclaration, forLoop, functionDeclaration, localProperty, outProjection,
primaryConstructor, propertyDeclaration, vararg */
