package com.zlrx.netty.nettyresearch.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.zlrx.netty.nettyresearch.model.Address
import com.zlrx.netty.nettyresearch.model.Employee
import com.zlrx.netty.nettyresearch.model.Person
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class EmployeeService constructor(
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var webClient: WebClient

    @PostConstruct
    fun init() {
        val strategies = ExchangeStrategies.builder().codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
        }.build()
        webClient = WebClient.builder()
            .exchangeStrategies(strategies)
            .baseUrl("http://localhost:8001")
            .build()
    }

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
            .doOnEach {
                logger.info("${it.get()} person arrived")
            }
    }

    private fun loadAddress(): Mono<Address> {
        return webClient.get()
            .uri("/address")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Address::class.java)
            .doOnEach {
                logger.info("${it.get()} address arrived")
            }
    }

}