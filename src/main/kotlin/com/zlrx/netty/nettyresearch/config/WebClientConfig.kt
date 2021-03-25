package com.zlrx.netty.nettyresearch.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.netty.tcp.TcpClient


@Configuration
class WebClientConfig {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun webClient(objectMapper: ObjectMapper, builder: WebClient.Builder): WebClient {
        val strategies = ExchangeStrategies.builder().codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
        }.build()


        val provider = ConnectionProvider.builder("netty-playground")
            .metrics(true)
            .build()

        val tcpClient = TcpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000)
            .doOnConnected {
                logger.info("Connected tcp")
                it.addHandler(ReadTimeoutHandler(2))
                    .addHandler(WriteTimeoutHandler(2))
            }

        return builder
            .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient).wiretap(true)))
            .exchangeStrategies(strategies)
            .baseUrl("http://localhost:8001")
            .build()
    }

}