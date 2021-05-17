package com.zlrx.netty.nettyresearch.native.nolib

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

fun main() {

    Thread {
        Server().run()
    }.start()

    Thread {
        Client().run()
    }.start()
    Thread.sleep(2000)
}

class Server() {

    private val address = InetSocketAddress("localhost", 8099)


    fun run() {
        val selector: Selector = Selector.open()
        val serverSocketChannel = ServerSocketChannel.open()
        serverSocketChannel.configureBlocking(false)
        serverSocketChannel.socket().bind(address)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)

        while (true) {
            selector.select()
            val readyKeys = selector.selectedKeys()
            val iterator = readyKeys.iterator()
            while (iterator.hasNext()) {
                val key = iterator.next()
                iterator.remove()
                if (key.isAcceptable) {
                    val msg = ByteBuffer.wrap("Hello socket".toByteArray())
                    val server = key.channel() as ServerSocketChannel
                    val client = server.accept()
                    client.configureBlocking(false)
                    client.register(selector, SelectionKey.OP_READ or SelectionKey.OP_WRITE, msg.duplicate())
                    println("Accept connection from $client")
                }
                if (key.isWritable) {
                    val client = key.channel() as SocketChannel
                    val buffer = key.attachment() as ByteBuffer
                    while (buffer.hasRemaining()) {
                        if (client.write(buffer) == 0) {
                            break
                        }
                    }
                    client.close()
                }
                key.cancel()
            }
        }
    }
}

class Client {

    private val address = InetSocketAddress("localhost", 8099)

    fun run() {
        val client = SocketChannel.open(address)

        val buffer = ByteBuffer.allocate(15)
        val read = client.read(buffer)
        for (i in 0 until read) {
            print(buffer[i].toChar())
        }
        println()
        client.finishConnect()
    }

}