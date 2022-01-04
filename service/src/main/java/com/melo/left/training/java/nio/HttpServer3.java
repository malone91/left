package com.melo.left.training.java.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * VM option -Xmx512m 防止崩溃
 * curl http://127.0.0.1:8803
 * sb -u http://127.0.0.1:8803 -c 40 -N 30
 *   RPS: 1499.3 (requests/second)
     Max: 164ms
     Min: 19ms
     Avg: 21.8ms

     50%   below 21ms
     60%   below 21ms
     70%   below 21ms
     80%   below 21ms
     90%   below 23ms
     95%   below 29ms
     98%   below 41ms
     99%   below 43ms
     99.9%   below 102ms

 创建线程的开销小 上下文切换资源小
 上下文切换比较大
 */
public class HttpServer3 {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8803);
        while (true) {
            Socket socket = serverSocket.accept();
            executorService.execute(() -> service(socket));
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type:text/html;charset=utf-8");
            writer.println();
            writer.println("hello nio");
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
