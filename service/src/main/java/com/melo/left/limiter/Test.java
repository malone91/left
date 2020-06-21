package com.melo.left.limiter;

import java.util.concurrent.CountDownLatch;

public class Test {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        //初始化对象池，大小姑且设置为10
        String[] message = new String[10];
        for (int i = 0; i < message.length; i++) {
            message[i] = "obj_" + i;
        }
        ObjectPool<String, String> pool = new ObjectPool<>(message);
        int executeCount = 0;
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    //定义Function并作为exec方法的参数
                    pool.exec(t -> {
                        System.out.println("当前线程id：" + Thread.currentThread().getId() + " ，当前对象T：" + t);
                        return t;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

            countDownLatch.countDown();

            executeCount++;
        }

        try {
            countDownLatch.await();
            System.out.println("共计执行" + executeCount + "次");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
