
// arrays must be of a single datatype
// arrays elements can be changed but the size is fixed
val rockPlanets = arrayOf<String>("mercury", "venus", "earth", "mars")
val gasPlanets = arrayOf("jupyter", "saturn", "uranus", "neptune") // type inference

// contcatenation
// val solarSystem = rockPlanets + gasPlanets
//
// lists -> ordered, resizable collection -> resizable array
// val solarSystem = listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
val solarSystem = mutableListOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

// sets
// "hashCode()" -> semi-unique identifier for kotlin object (hash collisions are possible)
// sets use hashCodes as array indicies
// sets make sure their contents are unique
// Set -> immutable; MutableSet -> qed

// maps
// mapOf(), mutableMapOf()
// mutableMapOf<keyType, valueType>()
val ss = mutableMapOf(
    "mercury" to 0,
    "venus" to 0,
    "earth" to 1,
    "mars" to 1,
    "jupyter" to 69,
    "saturn" to 420,
    "uranus" to 2,
    "neptune" to 42,
)


fun main() {
    // all these work for both 
    println("array size: ${solarSystem.size}")
    println("array[0]: ${solarSystem.get(0)}")
    println("earth: array[${solarSystem.indexOf("earth")}]")
    println()

    for (planet in solarSystem) {
        println(planet)
    }
    println()

    for (i in 0..7) {
        println(solarSystem[i])
    }
    println()

    // this only works if solarSystem is an array or MutableList
    solarSystem[4] = "jupiter"
    for (planet in solarSystem) println(planet)
    println()
    
    // adding and removing from a list
    solarSystem.removeAt(4)
    for (planet in solarSystem) println(planet)
    println()
    solarSystem.remove("Mercury")
    for (planet in solarSystem) println(planet)
    println()

    println(solarSystem.contains("Mars"))
    println("Moon" in solarSystem)

    println()
    println()

    println(ss.size)
    ss["pluto"] = "a" //not possible
    println(ss.size)
    println(ss["pluto"])
}
