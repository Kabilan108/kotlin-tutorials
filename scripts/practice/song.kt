
class Song (
    val title: String,
    val artist: String,
    val published: Int,
    var playCount: Int
) {
    val isPopular: Boolean
        get() = playCount >= 1000

    fun describe() {
        val popularity = when(isPopular) {
            true -> "popular"
            false -> "unpopular"
        }
        println("$title, performed by $artist, was released in $published [$popularity]")
    }
}

fun main() {
    val song = Song("hotel california", "the eagles", 1990, 1000)
    song.describe()
}
