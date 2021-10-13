package com.yh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : yh
 * @date : 2021/10/8 21:26
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //把 serverSocketChannel 注册到 selector 关注OP_ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {

            //等待一秒，如果没有事件发生，continue
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了一秒钟，无连接！");
                continue;
            }
            //如果有事件，则获取相关的selectionKey集合
            //selectionKeys 为关注事件的集合
            //可以通过selectionKey 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历selectionKeys
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                //获取到selectionKey
                SelectionKey selectionKey = selectionKeyIterator.next();

                //根据key 对应的通道发生的事件做相应的处理

                //1. OP_ACCEPT
                if (selectionKey.isAcceptable()) {
                    //为该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成一个SocketChannel->" + socketChannel.hashCode());
                    //将socketChannel 注册到selector，关注事件为OP_READ 同时关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    //2. OP_READ
                } else if (selectionKey.isReadable()) {
                    //通过selectionKey 获取 SocketChannel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取Buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("form 客户端 -> " + new String(byteBuffer.array()));
                }
                //最终手动移除selectionKey
                selectionKeyIterator.remove();
            }
        }
    }

}
