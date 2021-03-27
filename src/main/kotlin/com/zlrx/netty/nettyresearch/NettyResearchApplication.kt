package com.zlrx.netty.nettyresearch

import com.zlrx.netty.nettyresearch.config.ContextInitializer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.EnableAspectJAutoProxy
import reactor.tools.agent.ReactorDebugAgent


@SpringBootApplication
@EnableAspectJAutoProxy
class NettyResearchApplication

private val logger = LoggerFactory.getLogger("MAIN")

fun main(args: Array<String>) {
    ReactorDebugAgent.init()
    val applicationContext = SpringApplicationBuilder(NettyResearchApplication::class.java)
        .initializers(ContextInitializer())
        .run(*args)

    applicationContext.beanDefinitionNames.forEach {
        logger.info(it)
    }
    //runApplication<NettyResearchApplication>(*args)
}
