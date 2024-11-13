
/*
 *
 * dataclasses let the compiler make assumptions about classes and automatically 
 * implement certain methods (e.g. toString which is like __str__ for println)
 *
 * implements: equals(), hashCode(), toString(), componentN(), copy()
 * 
 * dataclasses can't be abstract, open, sealed or inner
 */

data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)
