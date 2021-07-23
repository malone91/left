package com.melo.left.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class DelayedQueueDemo {

    private static DelayQueue delayQueue = new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            delayQueue.offer(new MyDelayQueue("2", 2000));
            delayQueue.offer(new MyDelayQueue("1", 1000));
            delayQueue.offer(new MyDelayQueue("3", 3000));
            delayQueue.offer(new MyDelayQueue("4", 4000));
            delayQueue.offer(new MyDelayQueue("5", 5000));
            delayQueue.offer(new MyDelayQueue("6", 6000));
            delayQueue.offer(new MyDelayQueue("7", 7000));
            delayQueue.offer(new MyDelayQueue("14", 14000));
        }).start();

        while (true) {
            Delayed take = delayQueue.take();
            System.out.println(take);
        }
    }
}
