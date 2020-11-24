package attelass.openinghoursapi.formathours

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router(private val handler: Handler) {
    @Bean
    fun route() = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            POST("/format", handler::format)
        }
    }
}



