package com.zlrx.netty.nettyresearch.config

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext


class ContextInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun initialize(applicationContext: GenericApplicationContext) {
        definedBeans().initialize(applicationContext)
    }
}