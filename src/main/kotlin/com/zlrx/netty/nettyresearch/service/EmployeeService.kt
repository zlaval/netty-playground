package com.zlrx.netty.nettyresearch.service

import com.zlrx.netty.nettyresearch.model.Employee
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.SpanName
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
@SpanName("EmployeeService")
class EmployeeService constructor(
    private val addressService: AddressExternalService,
    private val peopleService: PeopleExternalService
) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @NewSpan(value = "EmployeeService")
    fun collectEmployees(): Flux<Employee> {
        logger.info("collect employees")

        val people = peopleService.loadPeople()
        val address = addressService.loadAddress()

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


}

