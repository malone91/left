package com.melo.left.netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * buffer仅支持buffer
 * buffer.asReadOnlyBuffer 只能读了，不能写了 HeapByteBufferR
 *
 */
public class BufferCopyDemo {

    /**
     * 两个channel，一个buffer
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("copy.txt");
        FileChannel inputChannel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("copyed.txt");
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            //标志位重置下 很重要 如果注释掉该行，则read始终为0陷入死循环
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read == -1) {
                break;
            } else {
                //buffer放入channel
                byteBuffer.flip();
                outputChannel.write(byteBuffer);
            }
        }
        inputStream.close();
        outputChannel.close();
    }
}
