package com.melo.left.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 令牌桶：容量b的含义是，限流器允许的最大突发流量
 * 令牌桶中的令牌已满，此时限流器允许10个请求同时通过限流器，当然只是突发流量而已，
 * 10个请求会带走10个令牌，后续的流量只能按照速率r通过限流器
 *
 * 如何实现呢？尤其是在高并发场景下
 * 没有使用定时器
 *
 * 记录并动态计算下一令牌发放的时间
 *
 */
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
