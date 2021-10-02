package com.yh.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从文件读数据
 * @author : yh
 * @date : 2021/9/27 22:42
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {
        //创建文件的输入流
        File file = new File("/Users/yh/Downloads/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过 FileOutputStream 获取 FileChannel  -> 实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入到Buffer
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
    }
}
