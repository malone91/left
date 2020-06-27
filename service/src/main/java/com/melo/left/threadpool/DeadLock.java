package com.melo.left.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLock {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            System.out.println("L1");
            es.execute(
                    () -> {
                        CountDownLatch latch2 = new CountDownLatch(2);
                        //执行第二阶段子任务
                        for (int j = 0; j < 2; j++) {
                            es.execute(
                                    () -> {
                                        System.out.println("L2");
                                        latch2.countDown();
                                    }
                            );
                        }
                        try {
                            latch2.await();
                        } catch (InterruptedException e) {

                        }
                        latch.countDown();
                    }
            );
        }
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        System.out.println("end");
        es.shutdown();
    }
}
