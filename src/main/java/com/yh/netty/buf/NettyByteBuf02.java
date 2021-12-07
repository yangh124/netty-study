package com.yh.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author : yh
 * @date : 2021/12/7 22:00
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,World!", StandardCharsets.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            String s = new String(content, StandardCharsets.UTF_8);
            System.out.println(s);

            System.out.println(byteBuf);

            //0
            System.out.println(byteBuf.arrayOffset());

            //0
            System.out.println(byteBuf.readerIndex());

            //12
            System.out.println(byteBuf.writerIndex());

            //36
            System.out.println(byteBuf.capacity());

            //可读字节数  12
            System.out.println(byteBuf.readableBytes());

            for (int i = 0; i < byteBuf.writerIndex(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
        }

    }

}
