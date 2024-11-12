// whats wrong with this file
 
fun main() {
    // c -> f
    printFinalTemperature(
        initialMeasurement = 27.0,
        initialUnit = "Celsius", 
        finalUnit = "Fahrenheit", 
    ) { it ->
        9.0 / 5.0 * it  + 32.0
    }
    // k -> c
    printFinalTemperature(
        initialMeasurement = 350.0,
        initialUnit = "Kelvin", 
        finalUnit = "Celsius", 
    ) { it ->
        it - 273.15
    }
    // f -> k
    printFinalTemperature(
        initialMeasurement = 10.0,
        initialUnit = "Fahrenheit", 
        finalUnit = "Kelvin", 
    ) { it ->
        5.0 / 9.0 * (it - 32.0) + 273.15
    }
}


fun printFinalTemperature(
    initialMeasurement: Double, 
    initialUnit: String, 
    finalUnit: String, 
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}

