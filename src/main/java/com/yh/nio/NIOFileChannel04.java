package com.yh.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * copy 文件
 * @author : yh
 * @date : 2021/10/2 11:12
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/yh/Downloads/a.png");
        FileChannel sourceChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/yh/Downloads/b.png");
        FileChannel targetChannel = fileOutputStream.getChannel();

        //copy 文件
        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        targetChannel.close();
        sourceChannel.close();
        fileOutputStream.close();
        fileInputStream.close();
    }

}
