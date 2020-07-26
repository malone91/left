package com.melo.left.designpattern.single;

import java.util.concurrent.atomic.AtomicLong;

/**
 * hungry
 * fail-fast及时暴露问题
 */
public class HungryIdGenerator {

    private AtomicLong id = new AtomicLong(0);
    private static final HungryIdGenerator ID_GENERATOR = new HungryIdGenerator();
    private HungryIdGenerator() {}

    public static HungryIdGenerator getInstance() {
        return ID_GENERATOR;
    }

    public long getId() {
        return id.getAndIncrement();
    }
}
