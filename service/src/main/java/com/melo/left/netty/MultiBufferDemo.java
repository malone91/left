package com.melo.left.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * NIO支持多个buffer(Buffer数组）完成读写操作
 * Scattering 和 Gathering 分散、合并
 * Scattering 将数据写入buffer时，可以采用buffer数组依次写入
 * Gathering 从buffer读取数据时，可以采用buffer数组依次读
 */
public class MultiBufferDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(7000);
        //绑定端口到socket并启动
        serverSocketChannel.bind(socketAddress);
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户端连接
        SocketChannel accept = serverSocketChannel.accept();
        int byteMessageSize = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < byteMessageSize) {
                long read = accept.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead" + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer ->
                        "position=" + buffer.position() + " ,limit=" + buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer ->
                    buffer.flip());
            //回显给客户端
            long byteWrite = 0;
            while (byteWrite < byteMessageSize) {
                long write = accept.write(byteBuffers);
                byteWrite += write;
                Arrays.asList(byteBuffers).stream().map(buffer ->
                        "position=" + buffer.position() + " ,limit=" + buffer.limit()).forEach(System.out::println);
            }

            //将所有buffer复位
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteWrite" + byteWrite);

        }
    }
}
