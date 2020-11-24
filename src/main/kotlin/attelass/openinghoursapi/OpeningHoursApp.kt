package attelass.openinghoursapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@SpringBootApplication
@EnableWebFlux
class OpeningHoursConfig : WebFluxConfigurer


fun main(args: Array<out String>) {
    SpringApplication.run(OpeningHoursConfig::class.java, *args)
}
