package com.melo.left.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 找到一个就返回了
 */
public class CompletionServiceTest2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executorService);
        List<Future<Integer>> futures = new ArrayList<>(3);
        futures.add(
            cs.submit(
                    () -> {
               TimeUnit.SECONDS.sleep(8);
                        return 8;
            })
        );
        futures.add(cs.submit(
                () -> {
                    TimeUnit.SECONDS.sleep(3);
                    return 3;
                }
        ));
        futures.add(
                cs.submit(() -> {
                    TimeUnit.SECONDS.sleep(10);
                    return 10;
                })
        );

        Integer result = 0;
        try {
            for (int i = 0; i < 3; i++) {
                result = cs.take().get();
                if (result != null) {
                    break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {

        } finally {
            for (Future<Integer> f : futures) {
                f.cancel(true);
            }
        }
        System.out.println(result);
        executorService.shutdown();
    }
}
