package com.yh.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : yh
 * @date : 2021/9/14 22:00
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        //创建一个线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动...");
        System.out.println("线程信息 id = " + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
        //监听，等待客户端连接
        System.out.println("等待连接...");
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //就创建一个线程，与之通信（单独写一个方法）
            threadPool.execute(() -> handler(socket));
        }
    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("read...");
                int read = inputStream.read(bytes);
                if (-1 != read) {
                    System.out.println("线程信息 id = " + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
                    System.out.println(new String(bytes, 0, read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
