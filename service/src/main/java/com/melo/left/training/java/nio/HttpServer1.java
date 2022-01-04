package com.melo.left.training.java.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * VM option -Xmx512m 防止崩溃
 * curl http://127.0.0.1:8801
 * sb -u http://127.0.0.1:8801 -c 40 -N 30    30秒  RPS requests per second 每秒并发请求才29，很慢，基本上不会超过50
 *   RPS: 47.9 (requests/second)  吞吐量低
     Max: 889ms
     Min: 88ms
     Avg: 812.5ms

     50%   below 822ms
     60%   below 823ms
     70%   below 824ms
     80%   below 825ms
     90%   below 827ms
     95%   below 834ms
     98%   below 837ms
     99%   below 838ms
     99.9%   below 868ms
 * 因为是串行执行，这是个单线程，只有一个主线程，而电脑是8核，一个线程也不合理
 */
public class HttpServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            Socket socket = serverSocket.accept();
            service(socket);
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
