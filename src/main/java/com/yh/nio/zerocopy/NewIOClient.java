package com.yh.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO客户端
 *
 * @author : yh
 * @date : 2021/10/17 12:48
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String fileName = "/Users/yh/Documents/data/jzlImportFiles.zip";
        FileChannel channel = new FileInputStream(fileName).getChannel();

        long l = System.currentTimeMillis();

        //在Linux下一个 transferTo 方法就能完成传输
        //在windows下一次调用 transferTo 只能发送8M的文件，就需要分段传输文件，而且要注意传输时的位置
        // transferTo  底层用的就是零拷贝
        long count = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送总字节数：" + count + "，耗时：" + (System.currentTimeMillis() - l) + " ms");

        channel.close();
        socketChannel.close();
    }

}
