// when statements
import java.util.Scanner

fun trafficLight() {
    val scanner = Scanner(System.`in`)
    print("Enter a color: ")
    val color = scanner.nextLine().lowercase()

    when (color) {
        "red" -> println("stop")
        "yellow" -> println("slow")
        "green" -> println("go")
        else -> println("invalid")
    }
}

fun trafficLight2() {
    val scanner = Scanner(System.`in`)
    print("Enter a color: ")
    val color = scanner.nextLine().lowercase()

    val message = when(color) {
        "red" -> "stop"
        "yellow", "amber" -> "slow"
        "green" -> "go"
        else -> "invalid"
    }

    println("message: $message")
}

fun numComp() {
    val number: Any = 1

    when (number) {
        2, 3, 5, 7 -> println("x is a prime between 1 and 10")
        in 1..10 -> println("x is between 1 and 10 but not prime")
        is Double -> println("x is a floating point number between 1 and 10")
        else -> println("else")
    }
}

fun main() {
    trafficLight2()
}

