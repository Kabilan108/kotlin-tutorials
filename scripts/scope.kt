
/*
 * scope functions are higher-order functions that let you access props & methods
 * of an object without refering to the objects name.
 * body of the function takes on the scope of the object that it was called with.
 *
 * behaves as if function were a method of the class
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

    // "let" lets you refer to an object in a labmda expression using it -> extension func for all types
    // fun printQuiz() {
    //     println(question1.questionText)
    //     println(question1.answer)
    //     println(question1.difficulty)
    //     println()
    //     println(question2.questionText)
    //     println(question2.answer)
    //     println(question2.difficulty)
    //     println()
    //     println(question3.questionText)
    //     println(question3.answer)
    //     println(question3.difficulty)
    //     println()
    // }

    fun printQuiz() {
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }
}


fun main() {
    // val quiz = Quiz()
    // quiz.printQuiz()
    //
    val quiz = Quiz().apply {
        printQuiz()
    }
    Quiz().apply {
        printQuiz()
    }
}
