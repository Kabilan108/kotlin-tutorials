// functions are 1st class ciizens

fun main() {
    // use the "function reference" to treat func as variable
    val trickRef = ::trick

    trick()
    trickRef()
    treat()

    // val coins: (Int) -> String = { quantity -> 
    //     "$quantity quarters"
    // }
    // val cake: (Int) -> String = { quantity -> 
    //     "$quantity cakes"
    // }
    //
    // use shorthand syntax:
    // when a func has one parameter and it is not named, kotlin assigns "it"
    // val coins: (Int) -> String = {
    //     "$it quarters"
    // }
    // val trickFunc = trickOrTreat(true, coins)
    //
    // you can also pass lambda expressions directly to functions
    // val trickFunc = trickOrTreat(true, { "$it quarters" })
    //
    // and finally, trailing lambda syntax
    val trickFunc = trickOrTreat(true) { "$it quarters" }
    val treatFunc = trickOrTreat(false, null)
    trickFunc()
    treatFunc()



    println("repeat functions")
    repeat(4) {
        treatFunc()
    }
    trickFunc()
}

// lambda function
// return type Unit \approc void
val treat: () -> Unit = {
    println("treats")
}

fun trick() {
    println("no treats")
}

// "higher order function" - takes or retruns func
fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)?): () -> Unit {
    if (isTrick) {
        return ::trick
    } else {
        if (extraTreat != null) {
            println(extraTreat(5))
        }
        return treat
    }
}
