
enum class DayPart {
    MORNING, AFTERNOON, EVENING
}

data class Event(
    val title: String,
    val description: String? = null,
    val daypart: DayPart,
    val duration: Int// minutes
)

val Event.durationStr: String
    get() {
        return when (duration) { 
            in 0..59 -> "short"
            else -> "long"
        }
    }

val eventList: MutableList<Event> = mutableListOf(
    Event(title = "Wake up", description = "Time to get up", daypart = DayPart.MORNING, duration = 60),
    Event(title = "Eat breakfast", daypart = DayPart.MORNING, duration = 15),
    Event(title = "Learn about Kotlin", daypart = DayPart.AFTERNOON, duration = 30),
    Event(title = "Practice Compose", daypart = DayPart.AFTERNOON, duration = 60),
    Event(title = "Watch latest DevBytes video", daypart = DayPart.AFTERNOON, duration = 10),
    Event(title = "Check out latest Android Jetpack library", daypart = DayPart.EVENING, duration = 45)
)

fun main() {
    val event = Event(
        title="Study Kotlin",
        description="Commit to studying Kotlin at least 15 minutes per day.",
        daypart=DayPart.EVENING,
        duration=15
    )
    println("$event")


    val shortEvents: List<Event> = eventList.filter {
        it.duration < 60
    }
    println("you have ${shortEvents.size} short events")

    val dayPartEvents: Map<DayPart, List<Event>> = eventList.groupBy {
        it.daypart
    }
    for (part in DayPart.values()) {
        println("$part: ${dayPartEvents[part]?.size} events")
    }
    dayPartEvents.forEach { (daypart, events) ->
        println("$daypart: ${events.size} events")
    }

    println("last event: ${eventList.last().title}")
    println("Duration of first event of the day: ${eventList[0].durationStr}")
}

