package com.yh.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : yh
 * @date : 2021/10/19 21:14
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        //创建BossGroup 和 WorkerGroup
        /*
         *说明：
         *  1. 创建两个线程组 bossGroup 和 workerGroup
         *  2. bossGroup 只处理连接请求，真正的客户端业务处理，会交给 workerGroup 完成
         *  3. 两个都是无限循环
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用 NioServerSocketChannel 作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道测试对象（匿名内部类）
                        //给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给 workerGroup 的 EventLoopGroup 对应的管道设置处理器

            System.out.println("...... server is ready ......");

            //绑定一个端口并且同步，生成一个 ChannelFuture 对象
            //启动服务器（并绑定端口）
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            //监听通道关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

}
