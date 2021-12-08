package com.yh.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author : yh
 * @date : 2021/12/8 21:52
 */
public class GroupChatClient {

    private final String host;

    private final int port;


    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new GroupChatClientHandler());
                    }
                });

        try {
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            Channel channel = sync.channel();
            System.out.println("------" + channel.localAddress() + "------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1", 8888).run();
    }
}
