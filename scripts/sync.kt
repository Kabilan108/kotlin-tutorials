package com.moberganalytics.tutorials

import kotlinx.coroutines.*

fun main() {
    // println("weather forecast")
    // // synchronous: printing is completed before moving on
    // // returns only when task is complete
    // println("sunny")

    println("weather forecast")
    // execution will pause for 1s then resume
    delay(1000)
    println("sunny")
}
