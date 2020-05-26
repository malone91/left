package com.melo.left.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionUseCase {

    private Lock lock;
    private Condition condition;

    public ConditionUseCase(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void conditionNotify() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
