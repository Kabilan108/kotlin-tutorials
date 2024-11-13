
class Cookie(
    val name: String,
    val softBaked: Boolean,
    val hasFilling: Boolean,
    val price: Double
) {
    override fun toString(): String {
        return "Cookie(name='$name', softBaked='$softBaked', hasFilling='$hasFilling', price=$price)"
    }
}

fun Cookie.toDisplayString(): String {
    return "Cookie(name='$name', softBaked='$softBaked', hasFilling='$hasFilling', price=$price)"
}

val cookies = listOf<Cookie>(
    Cookie(
        name = "Chocolate Chip",
        softBaked = false,
        hasFilling = false,
        price = 1.69
    ),
    Cookie(
        name = "Banana Walnut",
        softBaked = true,
        hasFilling = false,
        price = 1.49
    ),
    Cookie(
        name = "Vanilla Creme",
        softBaked = false,
        hasFilling = true,
        price = 1.59
    ),
    Cookie(
        name = "Chocolate Peanut Butter",
        softBaked = false,
        hasFilling = true,
        price = 1.49
    ),
    Cookie(
        name = "Snickerdoodle",
        softBaked = true,
        hasFilling = false,
        price = 1.39
    ),
    Cookie(
        name = "Blueberry Tart",
        softBaked = true,
        hasFilling = true,
        price = 1.79
    ),
    Cookie(
        name = "Sugar and Sprinkles",
        softBaked = false,
        hasFilling = false,
        price = 1.39
    )
)


fun main() {
    cookies.forEach {
        println("item: $it")
        println("item: ${it.toDisplayString()}")
    }

    val fullMenu: List<String> = cookies.map {
        "${it.name} - $${it.price}"
    }
    println("full menu:")
    fullMenu.forEach {
        println(it)
    }


    val softBakedMenu = cookies.filter {
        it.softBaked
    }
    println("soft baked menu:")
    softBakedMenu.forEach {
        println(it)
    }

    val groupedMenu = cookies.groupBy { it.softBaked }
    val softBakedMenu2 = groupedMenu[true] ?: listOf()
    val crunchyMenu = groupedMenu[false] ?: listOf()
    println(groupedMenu.keys)
    println("crunchy menu")
    crunchyMenu.forEach {
        println("${it.name} - ${it.price}")
    }

    println("soft baked menu")
    softBakedMenu2.forEach {
        println("${it.name} - ${it.price}")
    }

    println("cheap")
    val (cheap, expensive)  = cookies.partition { it.price < 1.4 }
    cheap.forEach {
        println(it)
    }


    // fold will produce single value from collection
    // pass initial value to fold
    // running totall will be passed as arg
    val totalPrice = cookies.fold(0.0) { total, cookie ->
        total + cookie.price
    }
    println("total price $${totalPrice}")
    // kotlin also has reduce() where the initial value stars with the first item
    // in the collection instaead of an initial value
    val alphabeticMenu = cookies.sortedBy {
        it.name
    }
    alphabeticMenu.forEach {
        println("${it.name} - ${it.price}")
    }
}

