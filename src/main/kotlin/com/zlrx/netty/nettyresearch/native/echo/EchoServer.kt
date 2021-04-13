package com.zlrx.netty.nettyresearch.native.echo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetSocketAddress


fun main() {
    EchoServer().start()
}

class EchoServer {

    private val port = 8000

    fun start() {
        val handler = EchoServerHandler()
        val eventLoopGroup = NioEventLoopGroup()
        try {
            val bs = ServerBootstrap()
            bs.group(eventLoopGroup)
                .channel(NioServerSocketChannel::class.java)
                .localAddress(InetSocketAddress(port))
                .childHandler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(handler)
                    }
                })

            val future = bs.bind().sync() //bind server and wait for completion
            future.channel().closeFuture().sync() //wait until channel closed
        } finally {
            eventLoopGroup.shutdownGracefully().sync() //release resources
        }
    }

}