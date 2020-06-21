package com.melo.left.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Test {

    public static void main(String[] args) {
        //也可以将该task放到Thread中
        FutureTask<Integer> futureTask = new FutureTask<Integer>(
                () -> 1 + 2
        );
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(futureTask);
        try {
            Integer integer = futureTask.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
