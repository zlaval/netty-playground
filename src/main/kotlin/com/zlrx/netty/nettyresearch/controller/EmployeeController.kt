package com.zlrx.netty.nettyresearch.controller

import com.zlrx.netty.nettyresearch.model.Employee
import com.zlrx.netty.nettyresearch.service.EmployeeService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Controller
class EmployeeController constructor(
    private val service: EmployeeService
) {

    @GetMapping(path = ["/employees"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPeople(): Mono<ResponseEntity<Flux<Employee>>> = Mono.just(ResponseEntity.ok(service.collectEmployees()))


}