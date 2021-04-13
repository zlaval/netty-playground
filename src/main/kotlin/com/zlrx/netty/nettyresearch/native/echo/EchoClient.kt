package com.zlrx.netty.nettyresearch.native.echo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress

fun main() {
    val echoClient = EchoClient()
    echoClient.start()
}


class EchoClient {

    private val port = 8000;
    private val host = "localhost";

    fun start() {
        val eventLoopGroup = NioEventLoopGroup()

        try {
            val bs = Bootstrap()
            bs.group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .remoteAddress(InetSocketAddress(host, port))
                .handler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(EchoClientHandler())
                    }
                })
            val future = bs.connect().sync()
            future.channel().closeFuture().sync()
        } finally {
            eventLoopGroup.shutdownGracefully().sync()
        }
    }


}