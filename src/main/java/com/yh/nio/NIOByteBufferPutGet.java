package com.yh.nio;

import java.nio.ByteBuffer;

/**
 * Buffer支持类型化的put和get，put放入的是什么数据类型，get就应该使用相应的数据类型来取出，否则可能有BufferUnderflowException
 *
 * @author : yh
 * @date : 2021/10/2 11:25
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(100);
        byteBuffer.putChar('c');
        byteBuffer.putLong(1L);
        byteBuffer.putShort((short) 2);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getLong());

        //System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getShort());
    }
}
