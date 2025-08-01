// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VARIABLE
// LATEST_LV_DIFFERENCE

fun println() {}
fun foo(x: Any) {}
fun <T> fooGeneric(x: T) {}

fun testResultOfLambda1() =
        run {
            if (true) 42 else println()
        }

fun testResultOfLambda2() =
        run {
            if (true) 42 else if (true) 42 else println()
        }

fun testResultOfAnonFun1() =
        run(fun () =
                if (true) 42
                else println()
        )

fun testResultOfAnonFun2() =
        run(fun () {
            if (true) 42 else println()
        })

fun testReturnFromAnonFun() =
        run(fun () {
            return <!RETURN_TYPE_MISMATCH, RETURN_TYPE_MISMATCH!>if (true) 42 else println()<!>
        })

fun <!IMPLICIT_NOTHING_RETURN_TYPE!>testReturn1<!>() =
        run {
            <!RETURN_IN_FUNCTION_WITH_EXPRESSION_BODY_WARNING!>return<!> <!RETURN_TYPE_MISMATCH!>if (true) 42
                   else println()<!>
        }

fun <!IMPLICIT_NOTHING_RETURN_TYPE!>testReturn2<!>() =
        run {
            <!RETURN_IN_FUNCTION_WITH_EXPRESSION_BODY_WARNING!>return<!> <!RETURN_TYPE_MISMATCH!>if (true) 42
                   else if (true) 42
                   else println()<!>
        }

fun testUsage1() =
        if (true) 42
        else println()

fun testUsage2() =
        foo(if (true) 42 else println())

fun testUsage2Generic() =
        fooGeneric(if (true) 42 else println())

val testUsage3 =
        if (true) 42
        else println()

val testUsage4: Any get() =
        if (true) 42 else println()

/* GENERATED_FIR_TAGS: anonymousFunction, functionDeclaration, getter, ifExpression, integerLiteral, lambdaLiteral,
nullableType, propertyDeclaration, typeParameter */
