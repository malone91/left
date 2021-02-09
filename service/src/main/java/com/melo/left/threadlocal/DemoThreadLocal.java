package com.melo.left.threadlocal;

import java.util.concurrent.TimeUnit;

public class DemoThreadLocal {

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set(12345);
        //12345
        System.out.println(threadLocal.get());
        new Thread(() -> {
            //null 子线程无法获取父线程本地变量的值
            System.out.println(threadLocal.get());
        }).start();
        TimeUnit.SECONDS.sleep(1);
    }
}