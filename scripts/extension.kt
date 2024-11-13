/* 
 * Extension properties -> 16.dp, 32.sp
 *
 * kotlin provides a way to add props & methods to an existing data type
 * these can be accessed with dot syntax
 *
 */

enum class Difficulty {
    EASY, MEDIUM, HARD
}

class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty,
)

interface ProgressPrintable {
    val progressText: String
    fun printProgressBar()
}

class Quiz : ProgressPrintable {
    val question1 = Question<String>(
        "Quoth the raven ___", "nevermore", Difficulty.MEDIUM
    )
    val question2 = Question<Boolean>(
        "The sky is green. True or false", false, Difficulty.EASY
    )
    val question3 = Question<Int>(
        "How many days are there between full moons?", 28, Difficulty.HARD
    )

    override val progressText: String
        get() = "${answered} of ${total} answered"

    override fun printProgressBar() {
        repeat(Quiz.answered) { print("▓") }
        repeat(Quiz.total - Quiz.answered) { print("▒") }
        println()
        println(progressText)
    }

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 9
    }
}

// define extension prop
// val typeName.propName : dataType
//      propertyGetter
// val Quiz.StudentProgress.progressText: String
//     get() = "${answered} of ${total} answered"


// extension function
// fun typeName.funcName(params) : returnType { funcBody }
// fun Quiz.StudentProgress.printProgressBar() {
//     repeat(Quiz.answered) { print("▓") }
//     repeat(Quiz.total - Quiz.answered) { print("▒") }
//     println()
//     println(Quiz.progressText)
// }

fun main() {
    // need to instantiate Quiz when implementing interface because function is defined
    // as part of a normal class not an object
    Quiz().printProgressBar()
    // use this with extension props
    // Quiz.printProgressBar()
}
