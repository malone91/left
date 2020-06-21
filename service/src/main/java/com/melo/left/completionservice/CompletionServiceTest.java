package com.melo.left.completionservice;

import java.util.concurrent.*;

public class CompletionServiceTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executorService);
        cs.submit(() -> {
            TimeUnit.SECONDS.sleep(12);
            return 12;
        });
        cs.submit(() -> {
            TimeUnit.SECONDS.sleep(1);
            return 13;
        });
        cs.submit(() -> {
            TimeUnit.SECONDS.sleep(1);
            return 14;
        });
        for (int i = 0; i < 3; i++) {
            Integer integer = cs.take().get();
            System.out.println(integer);
        }
    }
}
