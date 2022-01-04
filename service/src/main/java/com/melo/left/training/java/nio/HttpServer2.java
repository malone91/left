package com.melo.left.training.java.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * VM option -Xmx512m 防止崩溃
 * curl http://127.0.0.1:8802
 * sb -u http://127.0.0.1:8802 -c 40 -N 30
 *   RPS: 1376.2 (requests/second)
     Max: 176ms
     Min: 19ms
     Avg: 23.1ms

     50%   below 21ms
     60%   below 22ms
     70%   below 22ms
     80%   below 23ms
     90%   below 28ms
     95%   below 35ms
     98%   below 44ms
     99%   below 48ms
     99.9%   below 76ms

 问题：创建过多的线程，Java线程是裸线程，OS线程，线程使用效率特别低
 改进点：线程池跑代码
 */
public class HttpServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8802);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(()->{
                try {
                    service(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void service(Socket socket) throws IOException {
        try {
            Thread.sleep(20);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type:text/html;charset=utf-8");
            writer.println();
            writer.println("hello nio");
            writer.close();
            socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
