package com.melo.left.safecache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * HashMap不是线程安全的，使用读写锁保证其线程安全
 * @param <K>
 * @param <V>
 */
public class SafeCache<K, V> {

    final Map<K, V> map = new HashMap<>();
    final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final Lock readLock = readWriteLock.readLock();
    final Lock writeLock = readWriteLock.writeLock();

    /**
     * 获取
     * @param k
     * @return
     */
    V get(K k) {
        readLock.lock();
        try {
            return map.get(k);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 存放
     * @param k
     * @param value
     * @return
     */
    V put(K k, V value) {
        writeLock.lock();
        try {
            return map.put(k, value);
        } finally {
            writeLock.unlock();
        }
    }
}
