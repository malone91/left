package com.melo.left.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayQueue implements Delayed {

    private String name;
    private long start = System.currentTimeMillis();
    private long time;

    public MyDelayQueue(String name, long time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(start + time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        MyDelayQueue o1 = (MyDelayQueue)o;
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
