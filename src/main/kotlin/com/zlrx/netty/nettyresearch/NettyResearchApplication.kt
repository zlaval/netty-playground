package com.zlrx.netty.nettyresearch

import com.zlrx.netty.nettyresearch.config.startServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.tools.agent.ReactorDebugAgent

@SpringBootApplication
class NettyResearchApplication

fun main(args: Array<String>) {
    ReactorDebugAgent.init()
    startServer()
    runApplication<NettyResearchApplication>(*args)
}
