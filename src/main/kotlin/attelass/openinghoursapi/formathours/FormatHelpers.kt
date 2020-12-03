package attelass.openinghoursapi.formathours

import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun formatDates(weeklyOpeningData: WeeklyOpeningData): String = getWeeklyRanges(weeklyOpeningData)
    .groupByTo(emptyMap) { it.weekday }
    .toSortedMap()
    .map { formatDailyRanges(it.key, it.value) }
    .joinToString("\n")

private val emptyMap = Weekday.values()
    .sorted()
    .map { weekday -> weekday to mutableListOf<Range>() }
    .toMap()
    .toMutableMap()

private fun formatDailyRanges(weekday: Weekday, ranges: List<Range>): String {
    val weekdayString = weekday.name.toLowerCase().capitalize()
    val rangesString = when {
        ranges.isEmpty() -> "Closed"
        else -> ranges.joinToString(",", transform = Range::formatToString)
    }
    return "${weekdayString}: $rangesString"
}

private fun getWeeklyRanges(weeklyOpeningData: WeeklyOpeningData): List<Range> {
    val datedEvents = weeklyOpeningData
        .flatMap { (weekday, events) -> events.map { event -> event.toDatedEvent(weekday) } }
        .toList()
        .sortedWith(compareBy({ it.weekday }, { it.time }))
    val openingEvents = datedEvents.filterIsInstance<DatedEvent.OpenEvent>()
    val closeEvents = datedEvents.filterIsInstance<DatedEvent.CloseEvent>()

    return openingEvents.map { event ->
        val closeEvent = closeEvents.firstOrNull { it.weekday == event.weekday && it.time > event.time }
            ?: closeEvents.firstOrNull { it.weekday > event.weekday }
            ?: closeEvents.first() // In case we couldn't find any close event after this opening, start looking from the start of the week.
        Range(event.weekday, event.time, closeEvent.time)
    }
}

private data class Range(val weekday: Weekday, val opening: Int, val closed: Int)

private fun Range.formatToString(): String = "${timeFromValue(opening)} - ${timeFromValue(closed)}"

private fun timeFromValue(value: Int): String {
    val time = LocalTime.ofSecondOfDay(value.toLong())
    return time.format(DateTimeFormatter.ofPattern("h a"))
}
