package com.melo.left.thread;

public class DeadLockThread implements Runnable{

    private String first;
    private String second;

    public DeadLockThread(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first){
            System.err.println(Thread.currentThread().getName()+"锁"+first);
            try {
                Thread.sleep(500L);
                synchronized (second){
                    System.err.println(Thread.currentThread().getName()+"锁"+second);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String first = "lockA";
        String second = "lockB";
        Thread t1 = new Thread(new DeadLockThread(first, second));
        Thread t2 = new Thread(new DeadLockThread(first, second));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
