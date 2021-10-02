package com.yh.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个Buffer读写
 *
 * @author : yh
 * @date : 2021/10/2 10:57
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            //重置
            byteBuffer.clear();
            int read = fileChannel1.read(byteBuffer);
            if (-1 == read) {
                break;
            }
            //读 -> 写  flip
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
