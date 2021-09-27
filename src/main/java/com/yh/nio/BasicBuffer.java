package com.yh.nio;

import java.nio.IntBuffer;

/**
 * @author : yh
 * @date : 2021/9/22 21:40
 */
public class BasicBuffer {
    public static void main(String[] args) {

        //举例说明Buffer的使用（简单说明）
        //创建一个Buffer 大小为5  即可存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向Buffer 存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //从Buffer 读取数据
        //将Buffer转换，读写切换
        /*
        public final Buffer flip() {
            limit = position;
            position = 0;
            mark = -1;
            return this;
        }
         */
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.out.println(i);
        }

    }
}
