package com.yh.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO还提供了MappedByteBuffer，可以让文件直接在内存（堆外内存）中进行修改，而如何同步到文件由NIO来完成。
 *
 * @author : yh
 * @date : 2021/10/2 11:40
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        //p1: 读写模式
        //p2: 可以直接修改的起始位置
        //p3: 映射到内存的大小  即可以直接修改的范围 [0,5)
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        //报错 IndexOutOfBoundsException
        //mappedByteBuffer.put(5, (byte) 'T');

        randomAccessFile.close();


    }

}
