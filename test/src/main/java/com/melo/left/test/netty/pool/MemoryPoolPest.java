package com.melo.left.test.netty.pool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.stream.IntStream;

public class MemoryPoolPest {

    @Test
    public void testUnPooled() {
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(0, 100000000).forEach(
              e -> {
                  ByteBuf buffer = Unpooled.buffer(10 * 1024);
                  buffer.release();
              }
        );
        long end = System.currentTimeMillis();
        //120s
        System.out.println("dis pooled cost:" + (end - start) / 1000 + "秒");
    }

    @Test
    public void testPooled() {
        PooledByteBufAllocator allocator = new PooledByteBufAllocator();
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, 100000000).forEach(
                e -> {
                    ByteBuf heapBuffer = allocator.heapBuffer(10 * 1024);
                    heapBuffer.release();
                }
        );
        long end = System.currentTimeMillis();
        //11s
        System.out.println("pooled cost:" + (end - start) / 1000 + "秒");
    }
}
