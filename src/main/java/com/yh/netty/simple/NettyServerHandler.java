package com.yh.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;


/**
 * 继承 netty 某个Handler适配器 ，自定义一个handler
 *
 * @author : yh
 * @date : 2021/10/19 21:37
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取数据事件（这里我们可以读取客户端发送消息）
     * 1. ChannelHandlerContext ctx：上下文对象，含有管道 pipeline，通道 channel，连接地址
     * 2. Object msg：客户端发送的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //ByteBuf是netty提供的，不是 nio 的 ByteBuffer
        ByteBuf byteBuffer = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + byteBuffer.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端！喵喵喵！", StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
