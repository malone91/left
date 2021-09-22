package com.melo.left.netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ChannelCopyDemo {

    /**
     * 两个channel，一个buffer
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("copy.txt");
        FileChannel inputChannel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("copyed.txt");
        FileChannel outputChannel = outputStream.getChannel();

        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        inputChannel.close();
        outputChannel.close();
        inputStream.close();
        outputChannel.close();
    }

}
