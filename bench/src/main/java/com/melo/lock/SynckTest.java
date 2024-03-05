package com.melo.lock;

import java.util.concurrent.CountDownLatch;

public class SynckTest {

    private static int count;
    private static int num = 1;
    private static int maxValue = 1000;
    private final String lock = "lock";

    public static void main(String[] args) {
        Counter counter = new SynckTest().new Counter();
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
            synchronized (lock) {
                count++;
            }
        }
    }
}
