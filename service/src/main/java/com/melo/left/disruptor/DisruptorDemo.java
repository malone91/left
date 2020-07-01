package com.melo.left.disruptor;

/**
 * 高性能有界内存队列
 */
public class DisruptorDemo {

    public static void main(String[] args) {
        int bufferSize = 1024;
    }

    static class LongEvent {
        private long value;

        public void set(long value) {
            this.value = value;
        }
    }

}
