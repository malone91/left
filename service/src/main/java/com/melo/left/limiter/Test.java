package com.melo.left.limiter;

public class Test {

    public static void main(String[] args) {
        String[] message = new String[10];
        for (int i = 0; i < message.length; i++) {
            message[i] = "obj_" + i;
        }
        ObjectPool<String, String> pool = new ObjectPool<>(message);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    pool.exec(t -> {
                        System.out.println(Thread.currentThread().getId() + "当前对象T：" + t);
                        return t;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
