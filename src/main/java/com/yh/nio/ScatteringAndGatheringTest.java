package com.yh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * NIO还支持通过多个Buffer（即Buffer数组）完成读写操作，即Scattering（分散）和Gathering（合并）。
 * <p>
 * Scattering：将数据写入到Buffer中，可采用Buffer数组，依次写入 （分散）
 * Gathering：从Buffer读取数据时，可以采用Buffer数组，依次读出   （合并）
 *
 * @author : yh
 * @date : 2021/10/2 12:53
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {

        //使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        int msgLength = 8;

        while (true) {
            int byteRead = 0;

            while (byteRead < msgLength) {
                //会分散到buffer数组
                long read = socketChannel.read(byteBuffers);
                //累计读取的字节数
                byteRead += read;
                System.out.println("byteRead --> " + byteRead);
                //使用流打印，看看当前buffer的 position 和 limit
                Arrays.stream(byteBuffers).map(buffer -> "position = " + buffer.position() + "，limit = " + buffer.limit()).forEach(System.out::println);
            }
            //将所有Buffer进行flip
            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead = " + byteRead + "，byteWrite = " + byteWrite + "，msgLength = " + msgLength);
        }

    }
}
