package com.melo.left.threadpool.exception;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 1、execute方法,可以看异常输出在控制台，而submit在控制台没有直接输出，必须调用Future.get()方法时，可以捕获到异常。
   2、一个线程出现异常不会影响线程池里面其他线程的正常执行。
   3、线程不是被回收而是线程池把这个线程移除掉，同时创建一个新的线程放到线程池中。
 */
public class ThreadThrowsExceptionTest {

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(
                processors, processors, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new DefaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        //execute执行方式抛出异常显示在控制台, submit 啥都没有输出，submit底层其实也是调用的execute，submit返回一个future
        Future<?> future = executorService.submit(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + "要跑异常了");
            throw new NullPointerException("NPE");
        });
        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        IntStream.range(0, 4).forEach(index -> executorService.submit(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + "执行了" + index);
        }));
    }

    static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public DefaultThreadFactory() {
            SecurityManager manager = System.getSecurityManager();
            group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            System.out.println("创建线程：" +  t.getName());
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    }
}