package com.melo.left.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SelectorClientDemo {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        //连接成功发送数据
        ByteBuffer buffer = ByteBuffer.wrap("melo".getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
