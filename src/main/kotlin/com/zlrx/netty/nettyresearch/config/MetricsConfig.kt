package com.zlrx.netty.nettyresearch.config

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics
import org.springframework.context.support.beans

fun definedBeans() = beans {
    bean<ProcessMemoryMetrics>()
    bean<ProcessThreadMetrics>()
}

