package com.melo.left.designpattern.single;

import java.util.concurrent.atomic.AtomicLong;

public class InnerStaticClassIdGenerator {

    private AtomicLong id = new AtomicLong(0);
    private InnerStaticClassIdGenerator() {}

    private static class SingletonHolder {
        private static final InnerStaticClassIdGenerator instance = new InnerStaticClassIdGenerator();
    }

    /**
     * thread safe and lazy load
     * only when this method is invoked, static inner class will be
     * loaded and create instance,
     * the instance return is thread sale, unique because of jvm
     * @return
     */
    public static InnerStaticClassIdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.getAndIncrement();
    }
}
