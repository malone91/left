package com.melo.left.threadlocal;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadLocalOOM {

    public static final Integer SIZE = 500;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>()
    );

    static class Stu {
        private byte[] locla = new byte[1024 * 1024 * 5];
    }

    static ThreadLocal<Stu> local = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    local.set(new Stu());
                    System.out.println("开始执行");
                    local.get();
                    //防止泄露
                    local.remove();
                });
                Thread.sleep(100);
            }
            //置为null，造成堆内存泄露 25M内存泄露
            local = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}