package com.zlrx.netty.nettyresearch.service

import com.zlrx.netty.nettyresearch.model.Address
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class AddressExternalService(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @CircuitBreaker(name = "addressService", fallbackMethod = "addressFallback")
    fun loadAddress(): Mono<Address> {
        return webClient.get()
            .uri("/address")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Address::class.java)
            .doOnCancel {
                logger.info("Address cancelled")
            }
            .doOnError {
                logger.info("Address error: ${it.localizedMessage}" + it.localizedMessage)
            }
            .doOnEach {
                logger.info("Address doOnEach: ${it.type.name}")
            }
    }

    fun addressFallback(exception: Exception): Mono<Address> {
        logger.info("falback called to address")
        return Mono.just(Address(2000, "Budapest", "Bivalybasznad", 7))
    }
}