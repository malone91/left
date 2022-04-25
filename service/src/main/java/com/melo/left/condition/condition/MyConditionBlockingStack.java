package com.melo.left.condition.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConditionBlockingStack<T> extends MyStack<T> {
    public MyConditionBlockingStack(int capacity) {
        super(capacity);
    }

    private Lock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    @Override
    public void push(T item) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }
            doPush(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T pop() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            T t = doPop();
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}
