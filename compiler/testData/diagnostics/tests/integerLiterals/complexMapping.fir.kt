// DISABLE_JAVA_FACADE
// RUN_PIPELINE_TILL: BACKEND
// WITH_STDLIB
// FILE: First.java

public class First implements Comparable<First> {
    public static First compose(int something) {
        return null;
    }
}

// FILE: Second.java

public class Second implements Comparable<Second> {
    public static Second compose(String something) {
        return null;
    }
}

// FILE: complexMapping.kt

private fun <R : Comparable<R>> range(vararg ranges: Pair<R?, R?>): Ranges<R> = null!!

private abstract class Ranges<C : Comparable<C>> {
    abstract fun <M : Comparable<M>> map(transform: (C) -> M): Ranges<M>
}

private val INF: Nothing? = null

private val foo = listOf(
    range(0 to 1) to range(INF to ""),
    range(2 to 3) to range(INF to "", "" to INF),
    range(4 to 5) to range("" to INF),
    range(6 to INF) to range("" to INF)
).map {
    it.first.map(First::compose) to it.second.map(Second::compose)
}

/* GENERATED_FIR_TAGS: checkNotNullCall, classDeclaration, flexibleType, functionDeclaration, functionalType,
integerLiteral, javaCallableReference, lambdaLiteral, nullableType, propertyDeclaration, stringLiteral, typeConstraint,
typeParameter, vararg */
