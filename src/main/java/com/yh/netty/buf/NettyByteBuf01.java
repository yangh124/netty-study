package com.yh.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author : yh
 * @date : 2021/12/7 21:40
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {

        // 创建一个ByteBuf
        // 1. 该对象中包含一个byte[10]
        // 2. 在netty的ByteBuf，不需要使用flip方法进行反转
        // 底层维护了readerIndex 和 writerIndex
        // 3. 通过readerIndex、writerIndex和capacity。将buffer分成了3个区域
        //  0 <-----> readerIndex ： 已经读取的区域
        //  readerIndex <-----> writerIndex ： 可读的区域
        //  writerIndex <-----> capacity ：  可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

//        for (int i = 0; i < buffer.capacity(); i++) {
//            byte aByte = buffer.getByte(i);
//            System.out.println(aByte);
//        }

        for (int i = 0; i < buffer.capacity(); i++) {
            byte aByte = buffer.readByte();
            System.out.println(aByte);
        }
    }

}
