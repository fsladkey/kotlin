// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE
// FILE: A.java
public enum A {
    ENTRY,
    ANOTHER;
}

// FILE: test.kt
fun main() {
     checkSubtype<Array<A>>(A.values())
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, functionalType, infix,
javaFunction, javaType, nullableType, typeParameter, typeWithExtension */
