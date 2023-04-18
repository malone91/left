package com.melo.left.completablefuture;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestAsyncSync {

    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        System.out.println(LocalDateTime.now().getSecond());
        new Thread(() -> {
            try {
                Thread.sleep(7000);
                future.complete("done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        String s = null;
        try {
            s = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println(LocalDateTime.now().getSecond());
        System.out.println(s);
    }
}
