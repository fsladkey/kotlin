// RUN_PIPELINE_TILL: FRONTEND
//KT-607 Val reassignment is not marked as an error

package kt607

fun foo(a: A) {
    val o = object {
        val y : Int
           get() = 42
    }

    a.<!VAL_REASSIGNMENT!>z<!> = 23
    o.<!VAL_REASSIGNMENT!>y<!> = 11   //Should be an error here
}

class A() {
    val z : Int
    get() = 3
}

/* GENERATED_FIR_TAGS: anonymousObjectExpression, assignment, classDeclaration, functionDeclaration, getter,
integerLiteral, localProperty, primaryConstructor, propertyDeclaration */
