// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
package a

import java.lang.Deprecated as deprecated
import java.lang.SuppressWarnings as suppresswarnings


<!DEPRECATED_JAVA_ANNOTATION!>@deprecated<!> @suppresswarnings val s: String = "";

<!DEPRECATED_JAVA_ANNOTATION!>@deprecated<!> @suppresswarnings fun main() {
    System.out.println("Hello, world!")
}

class Test(<!DEPRECATED_JAVA_ANNOTATION!>@deprecated<!> val s: String,
           @suppresswarnings val x : Int) {}

/* GENERATED_FIR_TAGS: classDeclaration, flexibleType, functionDeclaration, javaFunction, javaProperty,
primaryConstructor, propertyDeclaration, stringLiteral */
