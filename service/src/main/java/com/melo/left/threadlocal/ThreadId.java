package com.melo.left.threadlocal;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ThreadLocal是线程封闭的
 *
 *
 * 同一个线程获取返回值相同
 * 不同的线程获取返回值不同
 *
 * 那 Java 的 ThreadLocal 是这么实现的吗？这一次我们的设计思路和 Java 的实现差异很大。
 * Java 的实现里面也有一个 Map，叫做 ThreadLocalMap，
 * 不过持有 ThreadLocalMap 的不是 ThreadLocal，
 * 而是 Thread。Thread 这个类内部有一个私有属性 threadLocals，
 * 其类型就是 ThreadLocalMap，ThreadLocalMap 的 Key 是 ThreadLocal。
 *
 * static class ThreadLocalMap{
 * //内部是数组而不是Map
 * Entry[] table;
 * //根据ThreadLocal查找Entry
 * Entry getEntry(ThreadLocal key){
 * //省略查找逻辑    }
 * //Entry定义
 * static class Entry extends
 * WeakReference<ThreadLocal>{
 * Object value;    }  }
 *
 * 每次线程执行的时候都去创建对象并存储在 ThreadLocal 中，用完就释放掉了，
 * 下次执行依然需要重新创建，并存入 ThreadLocalMap 中，这样并没有解决局部变量频繁创建对象的问题
 *
 * 这种用法一般是为了在一个线程里传递全局参数，也叫上下文信息，
 * 局部变量不能跨方法，这个用法不是用来解决局部变量重复创建的
 */
public class ThreadId {

    static final AtomicLong nextId = new AtomicLong(0);
    static final ThreadLocal<Long> th = ThreadLocal.withInitial(
            () -> nextId.getAndIncrement()
    );

    static long get() {
        return th.get();
    }
}
