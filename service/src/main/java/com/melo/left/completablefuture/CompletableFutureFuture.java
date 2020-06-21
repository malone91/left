package com.melo.left.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 默认情况下 CompletableFuture 会使用公共的 ForkJoinPool 线程池
 * 指定线程池在第二个参数里边
 *
 * ForkJoinPool在高并发场景里性能更高些
 */
public class CompletableFutureFuture {

    public static void main(String[] args) {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(
                () -> {
                    System.out.println("洗水壶");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("烧水");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("ccc");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("ddd");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("eee");
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "hello";
                }
        );

        //汇聚关系 AND
        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf)->{
            System.out.println("fff");
            return "fff";
        });
        System.out.println(f3.join());
    }
}
