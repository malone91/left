package com.melo.left.guardedobject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * //处理浏览器发来的请求
 * Respond handleWebReq(){
 * int id=序号生成器.get();
 * //创建一消息
 * Message msg1 = new Message(id,"{...}");
 * //创建GuardedObject实例
 * GuardedObject go= GuardedObject.create(id);
 * //发送消息 send(msg1);
 * //等待MQ消息  重写抽象方法
 * Message r = go.get( t->t != null); }
 * void onMessage(Message msg){
 * //唤醒等待的线程
 * GuardedObject.fireEvent( msg.id, msg);}
 *
 * 每个请求都会创建一个GuardedObject，get和onChanged不是在一个线程里执行的，也不在一个对象里
 *
 * 可以用“多线程版本的 if”来理解 Guarded Suspension 模式，
 * 不同于单线程中的 if，这个“多线程版本的 if”是需要等待的，而且还很执着，必须要等到条件为真。
 * 但很显然这个世界，不是所有场景都需要这么执着，有时候我们还需要快速放弃。
 * @param <T>
 */
public class GuardObject<T> {

    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 1;

    final static Map<Object, GuardObject> map = new ConcurrentHashMap<>();

    static <K> GuardObject create(K key) {
        GuardObject go = new GuardObject();
        map.put(key, go);

        return go;
    }

    static <K, T> void fireEvent(K key, T obj) {
        GuardObject go = map.remove(key);
        if (go != null) {
            go.onChange(obj);
        }
    }

    /**
     * 获取受保护对象
     * @param predicate
     * @return
     */
    T get(Predicate<T> predicate) {
        lock.lock();
        try {
            //MESA管程写法  本例中相当于 obj!=null
            while (!predicate.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return obj;
    }

    /**
     * 事件通知方法
     * @param obj
     */
    void onChange(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
