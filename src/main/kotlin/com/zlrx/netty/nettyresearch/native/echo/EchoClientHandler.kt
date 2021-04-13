package com.zlrx.netty.nettyresearch.native.echo

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.nio.charset.StandardCharsets

@ChannelHandler.Sharable
class EchoClientHandler : SimpleChannelInboundHandler<ByteBuf>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: ByteBuf) {
        println(msg.toString(StandardCharsets.UTF_8)) //print the received message
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(
            Unpooled.copiedBuffer(
                "Hello world",
                StandardCharsets.UTF_8
            )
        ) //send message when channel is activated
    }
}