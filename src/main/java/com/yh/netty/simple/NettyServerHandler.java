package com.yh.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


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
        /*
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看channel和pipeline的关系！");
        Channel channel = ctx.channel();
        //pipeline 本质是一个双向链表
        ChannelPipeline pipeline = ctx.pipeline();

        //ByteBuf是netty提供的，不是 nio 的 ByteBuffer
        ByteBuf byteBuffer = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + byteBuffer.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址：" + channel.remoteAddress());
        */

        /**
         * 当我们的业务耗时非常久的时候，就可以提交到该Channel对应的  NIOEventLoop 的 taskQueue 中
         */

        //解决方法1：用户程序自定义的普通任务，提交到taskQueue
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端！喵喵喵2！", StandardCharsets.UTF_8));
        });
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端！喵喵喵3！", StandardCharsets.UTF_8));
        });

        //解决方法1：用户自定义定时任务，提交到 scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端！喵喵喵4！", StandardCharsets.UTF_8));
        }, 5, TimeUnit.SECONDS);

        System.out.println("go on...");
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端！喵喵喵1！", StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
