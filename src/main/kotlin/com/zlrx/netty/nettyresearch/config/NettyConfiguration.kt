package com.zlrx.netty.nettyresearch.config

import io.netty.channel.nio.NioEventLoopGroup
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NettyConfiguration {


    @Bean
    fun eventLoopGroup(): NioEventLoopGroup {
        return NioEventLoopGroup(2)
    }

//    @Bean
//    fun factory(eventLoopGroup: NioEventLoopGroup){
//        val factory=NettyReactiveWebServerFactory()
//        factory.serverCustomizers= listOf(
//            NettyServerCustomizer {
//                    it.
//                }
//            }
//        )
//    }

    /*
    @Bean
    public NettyReactiveWebServerFactory factory(NioEventLoopGroup eventLoopGroup) {
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.setServerCustomizers(Collections.singletonList(new NettyServerCustomizer() {
            @Override
            public HttpServer apply(HttpServer httpServer) {
                return httpServer.tcpConfiguration(tcpServer ->
                                tcpServer.bootstrap(serverBootstrap -> serverBootstrap.group(eventLoopGroup)
                                                        .channel(NioServerSocketChannel.class)));
            }
        }));
        return factory;
    }
     */


}