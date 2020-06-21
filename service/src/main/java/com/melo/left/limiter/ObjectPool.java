package com.melo.left.limiter;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * 实现一个限流器
 * 控制N个线程同时访问线程池中的N个对象，其他线程无法访问线程池中的对象
 * @param <T>
 * @param <R>
 */
public class ObjectPool<T, R> {

    final List<T> pool;
    final Semaphore semaphore;

    public ObjectPool(T[] array) {
        pool = new Vector<>();
        int size = array.length;
        IntStream.rangeClosed(0, size - 1).forEach(e -> {
            pool.add(array[e]);
        });
        semaphore = new Semaphore(size);
    }

    R exec(Function<T, R> function) throws InterruptedException {
        T t = null;
        semaphore.acquire();
        System.out.println("当前信号量可用的许可数： " + semaphore.availablePermits());
        try {
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }
}
