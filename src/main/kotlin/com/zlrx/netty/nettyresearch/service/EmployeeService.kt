package com.zlrx.netty.nettyresearch.service

import com.zlrx.netty.nettyresearch.model.Address
import com.zlrx.netty.nettyresearch.model.Employee
import com.zlrx.netty.nettyresearch.model.Person
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.SpanName
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@SpanName("EmployeeService")
class EmployeeService constructor(
    private val webClient: WebClient
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @NewSpan(value = "EmployeeService")
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

//    private fun loadPeople(): Flux<Person> {
//        return webClient.get()
//            .uri("/people")
//            .accept(MediaType.APPLICATION_JSON)
//            .retrieve()
//            .bodyToFlux(Person::class.java)
//            .flatMap {
//                logger.info("${Thread.currentThread().name} in flatmap before publison")
//                Mono.just(it)
//            }
//            .doOnNext {
//                logger.info("$it")
//            }
//            .publishOn(Schedulers.boundedElastic())
//            .flatMap {
//                logger.info("${Thread.currentThread().name} in flatmap after publison")
//                Mono.just(it)
//            }
//            .doOnCancel {
//                logger.info("People was cancelled")
//            }
//    }

    private fun loadPeople(): Flux<Person> {
        return webClient.get()
            .uri("/people")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Person::class.java)
            .doOnCancel {
                logger.info("People was cancelled")
            }
    }

    private fun loadAddress(): Mono<Address> {
        return webClient.get()
            .uri("/address")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Address::class.java)
    }

}