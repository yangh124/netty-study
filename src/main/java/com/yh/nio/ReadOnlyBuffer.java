package com.yh.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer
 *
 * @author : yh
 * @date : 2021/10/2 11:34
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        //读取
        while(readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte) 1);
    }
}
