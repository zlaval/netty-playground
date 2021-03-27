package com.zlrx.netty.nettyresearch.service

import com.zlrx.netty.nettyresearch.model.Person
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.time.LocalDate

@Service
class PeopleExternalService(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @CircuitBreaker(name = "peopleService", fallbackMethod = "peopleFallback")
    fun loadPeople(): Flux<Person> {
        return webClient.get()
            .uri("/people")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Person::class.java)
            .doOnCancel {
                logger.info("People was cancelled")
            }
    }

    private fun peopleFallback(exception: Exception): Flux<Person> {
        logger.info("fallback called to people")
        return Flux.just(Person("Fallback Joe", LocalDate.now()))
    }
}