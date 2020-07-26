package com.melo.left.designpattern.single;

import java.util.concurrent.atomic.AtomicLong;

/**
 * high jdk version forbids command re-order
 * so volatile is not necessary at line 12
 */
public class LazyIDGenerator {

    private AtomicLong id = new AtomicLong(0);
    private static volatile LazyIDGenerator instance;
    private LazyIDGenerator() {}

    public static LazyIDGenerator getInstance() {
        if (instance == null) {
            synchronized (LazyIDGenerator.class) {
                if (instance == null) {
                    instance = new LazyIDGenerator();
                }
            }
        }
        return instance;
    }

    public long getId() {
        return id.getAndIncrement();
    }
}
