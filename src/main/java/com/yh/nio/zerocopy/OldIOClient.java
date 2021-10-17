package com.yh.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author : yh
 * @date : 2021/10/17 12:27
 */
public class OldIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);
        String fileName = "/Users/yh/Documents/data/jzlImportFiles.zip";
        FileInputStream fileInputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[4096];
        long read;
        long total = 0;

        long l = System.currentTimeMillis();

        while ((read = fileInputStream.read(bytes)) >= 0) {
            total += read;
            dataOutputStream.write(bytes);
        }

        System.out.println("发送总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - l)+" ms");

        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }

}
