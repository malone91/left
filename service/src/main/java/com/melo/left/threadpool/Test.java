package com.melo.left.threadpool;

import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) {
        //just test create
        ExecutorService es = new ThreadPoolExecutor(
                50, 500,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                r -> {
                    return new Thread(r, "echo-" + r.hashCode());
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        //也可以将该task放到Thread中
        FutureTask<Integer> futureTask = new FutureTask<>(
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
