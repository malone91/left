package com.melo.left.config.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class CustomizeThreadPoolExecutor {

    @Bean("myThreadPoolExecutor")
    public ExecutorService getMyThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "my-" + count++);
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0, Runtime.getRuntime().availableProcessors(), 1L, TimeUnit.HOURS,
                new SynchronousQueue<>(), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }
}
