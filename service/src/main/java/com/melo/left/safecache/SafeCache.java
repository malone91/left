package com.melo.left.safecache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 来自GeekTime Chapter 17 of Concurrent Program Practice
 * HashMap不是线程安全的，使用读写锁保证其线程安全
 * ReadWriteLock不支持锁的升级，读锁没有释放时，获取写锁，会导致写锁永久等待，最终导致相关的线程阻塞，
 * 永远没有机会被唤醒。所以不允许读写锁的升级。
 * 但是锁的降级是允许的
 *
 * 线上系统停止响应了，CPU 利用率很低：读锁升级写锁导致阻塞？
 * 代码查找：线上是Web应用，应用服务器比如说是Tomcat，并且开启了JMX，则可以通过JConsole等工具远程查看下线上死锁的具体情况
 *
 * ReentrantLock只有写锁支持条件变量，读锁不支持条件变量
 *
 * 不足：没有保证同步，即缓存和源头数据的一致性
 * 最简单的解决方案：超时机制，数据库主动改动缓存
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
