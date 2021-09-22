package com.melo.left.netty;


import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO提供了MappedByteBuffer 让文件直接在内存直接修改，堆外内存
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("copy.txt", "rw");
        FileChannel channel = file.getChannel();
        //使用读写模式，可以修改的起始位置，映射到内存的大小文件的多少个字节映射到内存
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        map.put(4, (byte) '9');
        file.close();
    }
}
