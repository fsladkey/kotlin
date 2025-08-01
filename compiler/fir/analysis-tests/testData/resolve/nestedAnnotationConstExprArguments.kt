// RUN_PIPELINE_TILL: BACKEND
annotation class InnerAnnotation(val text: String)
annotation class OuterAnnotation(val inner: InnerAnnotation)

@OuterAnnotation(InnerAnnotation(text = "x" + "x"))
class Payload

@InnerAnnotation(text = "x" + "x")
class Payload2

@OuterAnnotation(InnerAnnotation(text = "x"))
class Payload3

@OuterAnnotation(InnerAnnotation("x" + "x"))
class Payload4

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, primaryConstructor, propertyDeclaration, stringLiteral */
