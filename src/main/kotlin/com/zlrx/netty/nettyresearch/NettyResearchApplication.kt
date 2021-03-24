package com.zlrx.netty.nettyresearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.tools.agent.ReactorDebugAgent


@SpringBootApplication
//@EnableZipkinServer
class NettyResearchApplication {


}

fun main(args: Array<String>) {
    ReactorDebugAgent.init()
    // startServer()
    runApplication<NettyResearchApplication>(*args)
}
