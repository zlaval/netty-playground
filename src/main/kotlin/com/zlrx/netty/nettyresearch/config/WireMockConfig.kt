package com.zlrx.netty.nettyresearch.config

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer

fun startServer(): WireMockServer {
    val config = WireMockConfiguration.options()
        .port(8001)
        .usingFilesUnderClasspath("wiremock")
        .extensions(ResponseTemplateTransformer(true))

    val wireMockServer = WireMockServer(config)
    wireMockServer.start()
    return wireMockServer
}