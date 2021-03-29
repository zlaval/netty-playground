package com.zlrx.netty.nettyresearch.config

import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultThreadFactory
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.http.server.HttpServer

class NettyCustomizers : NettyServerCustomizer {

    private val parentGroup = NioEventLoopGroup(3, DefaultThreadFactory("boss"))
    private val workerGroup = NioEventLoopGroup(8, DefaultThreadFactory("worker"))

    override fun apply(t: HttpServer): HttpServer {

//        t.accessLog(true)
//        t.tcpConfiguration {
//            it.runOn(parentGroup)
//        }
//        t.runOn(workerGroup)
        return t
    }

}

@Configuration
class NettyConfiguration {

    @Bean
    fun nettyFactory(): NettyReactiveWebServerFactory {
        val factory = NettyReactiveWebServerFactory()
        factory.addServerCustomizers(NettyCustomizers())
        return factory
    }
}