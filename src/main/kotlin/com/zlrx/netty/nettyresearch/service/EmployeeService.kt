package com.zlrx.netty.nettyresearch.service

import com.zlrx.netty.nettyresearch.model.Address
import com.zlrx.netty.nettyresearch.model.Employee
import com.zlrx.netty.nettyresearch.model.Person
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Service
class EmployeeService constructor(
    private val webClient: WebClient
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun collectEmployees(): Flux<Employee> {
        logger.info("collect employees")

        val people = loadPeople()
        val address = loadAddress()

        return address.flatMapMany { a ->
            people.map { p ->
                Employee(p.name, p.birth, a.toString())
            }
        }
    }

    private fun loadPeople(): Flux<Person> {
        return webClient.get()
            .uri("/people")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Person::class.java)
            .subscribeOn(Schedulers.boundedElastic())
            .doOnCancel {
                logger.info("People was cancelled")
            }
            //.timeout(Duration.ofMillis(100))
    }

    private fun loadAddress(): Mono<Address> {
        return webClient.get()
            .uri("/address")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Address::class.java)
    }

}