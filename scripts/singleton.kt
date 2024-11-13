
// "object" -> make a class a singleton
// no constructor -> all props are given an initial value

object StudentProgress {
    var total: Int = 10
    var answered: Int = 0
}


// classes & objects can be defijned inside other types 
// a singleton object inside another class -> companion object
// this will let you access props & methods of the companion object from the parent class

enum class Difficulty {
    EASY, MEDIUM, HARD
}

class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty,
)

class Quiz {
    val question1 = Question<String>(
        "Quoth the raven ___", "nevermore", Difficulty.MEDIUM
    )
    val question2 = Question<Boolean>(
        "The sky is green. True or false", false, Difficulty.EASY
    )
    val question3 = Question<Int>(
        "How many days are there between full moons?", 28, Difficulty.HARD
    )

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }
}

fun main() {
    println("answered ${Quiz.answered} of ${Quiz.total}")
}
