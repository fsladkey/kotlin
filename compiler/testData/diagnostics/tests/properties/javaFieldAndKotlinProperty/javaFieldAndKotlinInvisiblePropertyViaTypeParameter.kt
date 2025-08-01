// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: -ProperFieldAccessGenerationForFieldAccessShadowedByKotlinProperty
// ISSUE: KT-56386

// FILE: BaseJava.java
public class BaseJava {
    public String a = "OK";

    public String foo() {
        return a;
    }
}

// FILE: Derived.kt
open class Derived : BaseJava() {
    private val a = "FAIL"
}

fun <T : Derived> test(t: T): String {
    val first = t.a
    if (first != "OK") return first
    if (t::a.get() != "OK") return t::a.get()
    t.a = "12"
    if (t.foo() != "12") return "Error writing: ${t.foo()}"
    return "OK"
}

fun box(): String = test(Derived())

/* GENERATED_FIR_TAGS: assignment, classDeclaration, equalityExpression, flexibleType, functionDeclaration, ifExpression,
javaCallableReference, javaFunction, javaProperty, javaType, localProperty, propertyDeclaration, stringLiteral,
typeConstraint, typeParameter */
