package com.melo.left.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * Thread类中生命了 inheritableThreadLocals变量
 * if (inheritThreadLocals && parent.inheritableThreadLocals != null)
    this.inheritableThreadLocals =
    ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);

 ThreadLocal复制ThreadLocalMap parentMap

    存储自定义对象会出现问题
    public class MyInHeritableThreadLocal<T> extends InHeritableThreadLocal<T> {
        protected T childValue(T parentValue) {
            String s = JSONObject.toJSONString(parentValue);
            return (T)JSONObject.parseObject(s, parentValue.getClass());
 //实现了深拷贝
 }
 }
 */
public class DemoInheritableThreadLocal {

    /**
     * ITL是在创建子线程的时候拷贝父线程的本地变量到子线程中
     *
     * 在线程池中，如果在提交任务的时候子线程都拷贝父线程的本地变量怎么办呢？
     * 答案是 TransmittableThreadLocal
     * 阿里开源，在使用线程池等会缓存线程的组件情况下传递ThreadLocal问题ITL的扩展 TTL
     */
    public static ThreadLocal<Integer> inThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        inThreadLocal.set(12345);
        //12345
        System.out.println(inThreadLocal.get());

        new Thread(() -> {
            //12345 子线程将父线程的本地变量复制到了子线程的本地变量中
            System.out.println(inThreadLocal.get());
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }
}