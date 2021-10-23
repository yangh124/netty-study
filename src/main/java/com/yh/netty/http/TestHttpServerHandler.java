package com.yh.netty.http;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * 自定义handler
 * <p>
 * SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter
 *
 * @author : yh
 * @date : 2021/10/23 11:25
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断 msg 是不是 httpRequest
        if (msg instanceof HttpRequest) {

            HttpRequest httpRequest = (HttpRequest) msg;

            String uri = httpRequest.getUri();
            if ("/favicon.ico".equals(uri)) {
                System.out.println(uri);
                return;
            }

            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            ByteBuf byteBuf = Unpooled.copiedBuffer("hello，我是服务器！", StandardCharsets.UTF_8);

            //构建一个 httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            ctx.writeAndFlush(response);
        }
    }

}
