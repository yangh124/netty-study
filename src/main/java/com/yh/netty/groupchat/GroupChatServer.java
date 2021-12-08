package com.yh.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author : yh
 * @date : 2021/12/8 21:10
 */
public class GroupChatServer {

    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup bossEvenLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerEvenLoopGroup = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossEvenLoopGroup, workerEvenLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new GroupChatServerHandler());
                    }
                });

        System.out.println("netty 服务器启动");

        try {
            ChannelFuture sync = serverBootstrap.bind(port).sync();

            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossEvenLoopGroup.shutdownGracefully();
            workerEvenLoopGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws InterruptedException {
        GroupChatServer groupChatServer = new GroupChatServer(8888);
        groupChatServer.run();
    }

}
