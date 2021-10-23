package com.yh.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author : yh
 * @date : 2021/10/23 11:26
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //得到 pipeline
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty 提供的 httpServerCodec  编码、解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec())
                .addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
