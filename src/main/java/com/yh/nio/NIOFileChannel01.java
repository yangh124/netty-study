package com.yh.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @author : yh
 * @date : 2021/9/27 22:26
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str = "hello,World!";
        //创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/yh/Downloads/file01.txt");
        //通过 fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入 byteBuffer
        byteBuffer.put(str.getBytes());
        // flip
        byteBuffer.flip();
        //将 byteBuffer 写入到 fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
