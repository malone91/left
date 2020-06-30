package com.melo.left.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterTest {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1.0);
        ExecutorService service = Executors.newFixedThreadPool(1);
        long time = System.nanoTime();
        for (int i = 0; i < 20; i++) {
            rateLimiter.acquire();
            service.execute(() -> {
                System.out.println("aaa");
            });
        }
    }
}
