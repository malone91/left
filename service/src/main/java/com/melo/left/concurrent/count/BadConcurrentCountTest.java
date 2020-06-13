package com.melo.left.concurrent.count;

public class BadConcurrentCountTest {

    private long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count++;
        }
    }

    public static long calculate() throws InterruptedException {
        final BadConcurrentCountTest test = new BadConcurrentCountTest();
        Thread thread1 = new Thread(() -> {
           test.add10K();
        });

        Thread thread2 = new Thread(() -> {
            test.add10K();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        return test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        long count = BadConcurrentCountTest.calculate();
        System.out.println(count);
    }
}
