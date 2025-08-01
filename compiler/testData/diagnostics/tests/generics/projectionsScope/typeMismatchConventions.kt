// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

class A<T> {
    operator fun plus(x: Out<T>): A<T> = this
    operator fun set(x: Int, y: Out<T>) {}
    operator fun get(x: Out<T>) = 1
}

class Out<out F>

fun test(a: A<out CharSequence>, y: Out<CharSequence>) {
    a + <!TYPE_MISMATCH!>y<!>
    a[1] = <!TYPE_MISMATCH!>y<!>
    a[<!TYPE_MISMATCH!>y<!>]

    a + Out<Nothing>()
    a[1] = Out<Nothing>()
    a[Out<Nothing>()]
}

/* GENERATED_FIR_TAGS: additiveExpression, assignment, capturedType, classDeclaration, functionDeclaration,
integerLiteral, nullableType, operator, out, outProjection, thisExpression, typeParameter */
