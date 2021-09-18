package com.melo.left.netty;

import java.nio.IntBuffer;

public class ByteBufferTest {

    /**
     * position limit capacity mark
     * capacity容量不可变，limit缓冲区的当前终点极限<=size，position下一个要被读或写的索引，读时发生变化，写会发生变化 ，mark用的少
     * 以块的方式处理数据，有了buffer就可以非阻塞式的处理数据
     * NIO基于Channel通道和Buffer缓冲区进行操作，数据总是从通道读取到缓冲区中，
     * 或者从缓冲区写入到通道中。Selector选择器用于监听多个通道的事件，比如连接，数据到达，
     * 从而一个线程可以监听多个客户端通道。你不活跃，但我并不会阻塞。
     * BIO基于字节流、字符流
     *
     * 一个线程对应多个selector，selector下有多个channel（可以理解为连接），每一个channel对应一个buffer，一个buffer对应一个client
     * 多个channel会注册到selector上
     * 程序切换到哪个channel，是由事件决定的 Event
     * selector会根据不同的事件在各个channel通道上切换
     * buffer本质是一个内存块 NIO面向它 底层是一个数组
     * 数据的读取都是要通过buffer BIO是单向流，入或出， buffer是可以读也可以写，需要flip切换
     * channel也是非阻塞的双向的，可以反映底层操作系统情况，Linux底层OS通道就是双向的
     * NIO是事件驱动的
     *
     * 缓冲区buffer本质上是一个可以读写数据的内存块，容器对象（数组），轻松使用内存块，记录跟踪缓冲区状态变化情况，channel提供
     * 从文件网络读取数据 的渠道，但是读取或者写入的数据必须经由buffer
     * nio程序 -- data -- buffer -- channel -- file
     * @param args
     */
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        //write
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2);
        }
        //read 将buffer读写切换一下  标记会发生变化 读数据不能超过5，把position赋值给limit，position变为0
        buffer.flip();
        while (buffer.hasRemaining()) {
            //get里维护了一个索引，每get一次之后索引就会移动一次，更新
            System.out.println(buffer.get());
        }
    }
}
