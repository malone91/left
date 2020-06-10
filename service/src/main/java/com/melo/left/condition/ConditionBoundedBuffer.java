package com.melo.left.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示条件变量的有界缓存
 * @param <T>
 */
public class ConditionBoundedBuffer<T> {

    protected final Lock lock = new ReentrantLock();
    /** define two condition predicate objects */
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final T[] items = (T[])new Object[32];
    private int tail, head, count;

    /**
     * 阻塞并直到notFull
     * @param x
     * @throws InterruptedException
     */
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            //满了，等待不满
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            //数组最后一个元素里的坑了，有界缓存直接替换第一个
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
            //至少有一个占坑，有值了
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            //空了，等待不空
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            //缓存take到数组结尾了
            if (++head == items.length) {
                head = 0;
            }
            --count;
            //take完至少有一个坑了
            notFull.await();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
