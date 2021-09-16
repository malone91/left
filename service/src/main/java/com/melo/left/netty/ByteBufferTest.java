package com.melo.left.netty;

import java.nio.IntBuffer;

public class ByteBufferTest {

    /**
     * position limit capacity mark
     * 以块的方式处理数据，有了buffer就可以非阻塞式的处理数据
     * NIO基于Channel通道和Buffer缓冲区进行操作，数据总是从通道读取到缓冲区中，
     * 或者从缓冲区写入到通道中。Selector选择器用于监听多个通道的事件，比如连接，数据到达，
     * 从而一个线程可以监听多个客户端通道。你不活跃，但我并不会阻塞。
     * BIO基于字节流、字符流
     * @param args
     */
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        //write
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2);
        }
        //read 将buffer读写切换一下  标记会发生变化
        buffer.flip();
        while (buffer.hasRemaining()) {
            //get里维护了一个索引，每get一次之后索引就会移动一次，更新
            System.out.println(buffer.get());
        }
    }
}
