//package com.zlrx.netty.nettyresearch.config
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
//import io.github.resilience4j.timelimiter.TimeLimiterConfig
//import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
//import org.springframework.cloud.client.circuitbreaker.Customizer
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import java.time.Duration
//
//@Configuration
//class CircuitBreakerConfig {
//
//    @Bean
//    fun circuitBreakerCustomizer(): Customizer<ReactiveResilience4JCircuitBreakerFactory> {
//        return Customizer { cbf ->
//            cbf.configureDefault { id ->
//                Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100)).build())
//                    .build()
//            }
//        }
//    }
//
//}