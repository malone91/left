package com.melo.left.designpattern.single;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumIdGenerator {

    INSTANCE;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.getAndIncrement();
    }
}
