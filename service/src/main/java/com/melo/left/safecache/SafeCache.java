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
     * 按需加载获取
     * @param k
     * @return
     */
    V get(K k) {
        V v = null;
        readLock.lock();
        try {
            v = map.get(k);
        } finally {
            readLock.unlock();
        }
        if (v != null) {
            //缓存中存在则返回
            return v;
        }
        //缓存中不存在则查DB
        writeLock.lock();
        try {
            //其他线程可能已经查询过数据库
            v = map.get(k);
            if (v == null) {
//                v = "from db";
                map.put(k, v);
            }
        } finally {
            writeLock.unlock();
        }

        return v;
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
