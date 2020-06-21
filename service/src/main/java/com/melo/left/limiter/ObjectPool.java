package com.melo.left.limiter;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;

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
        try {
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }
}
