package attelass.openinghoursapi.formathours

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.annotation.JsonValue

enum class Weekday(@JsonValue val key: String) {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday")
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed class OpeningEvent(val value: Int) {
    @JsonTypeName("open")
    class Open(value: Int) : OpeningEvent(value)
    @JsonTypeName("close")
    class Closed(value: Int) : OpeningEvent(value)
}

sealed class DatedEvent(val weekday: Weekday, val time: Int) {
    class OpenEvent(weekday: Weekday, value: Int) : DatedEvent(weekday, value)
    class CloseEvent(weekday: Weekday, value: Int) : DatedEvent(weekday, value)
}

fun OpeningEvent.toDatedEvent(weekday: Weekday) = when (this) {
    is OpeningEvent.Open -> DatedEvent.OpenEvent(weekday, value)
    is OpeningEvent.Closed -> DatedEvent.CloseEvent(weekday, value)
}
