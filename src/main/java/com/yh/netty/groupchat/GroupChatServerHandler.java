package com.yh.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @author : yh
 * @date : 2021/12/8 21:22
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE是全局事件执行器，是一个单例
     */
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //将该客户端加入聊天的信息推送给其他在线的客户端
        // writeAndFlush 将消息发送给CHANNEL_GROUP中的所有客户端
        CHANNEL_GROUP.writeAndFlush("[客户端]：" + channel.remoteAddress() + " 加入了群聊");
        CHANNEL_GROUP.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //将该客户端加入聊天的信息推送给其他在线的客户端
        // writeAndFlush 将消息发送给CHANNEL_GROUP中的所有客户端
        CHANNEL_GROUP.writeAndFlush("[客户端]：" + channel.remoteAddress() + " 退出了群聊");
        //这里不需要执行remove。会自动remove
    }

    /**
     * 表示xxx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了～");
    }

    /**
     * 表示xxx下线
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了～");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel currentChannel = ctx.channel();
        for (Channel channel : CHANNEL_GROUP) {
            if (currentChannel != channel) {
                channel.writeAndFlush("[" + currentChannel.remoteAddress() + "]：" + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
