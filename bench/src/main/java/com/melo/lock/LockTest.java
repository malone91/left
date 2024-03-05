package com.melo.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private static int count;
    private static int num = 1;
    private static int maxValue = 1000;
    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Counter counter = new LockTest().new Counter();
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(()->{
                for (int j = 0; j < maxValue; j++) {
                    counter.add();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("执行时长 " + (endTime - startTime) + " count: " + counter);
    }

    class Counter {
        public void add() {
            lock.lock();
            try {
                count++;
            }finally {
                lock.unlock();
            }
        }
    }
}
