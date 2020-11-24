package attelass.openinghoursapi.formathours

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerRequest


@Component
class Handler {
    suspend fun format(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<WeeklyOpeningData>()
        val formatted = formatDates(body)
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValueAndAwait(formatted)
    }
}

typealias WeeklyOpeningData = Map<Weekday, List<OpeningEvent>>
