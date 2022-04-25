package com.melo.left.condition.condition;

public class MyBlockingStack<T> extends MyStack<T> {

    public MyBlockingStack(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized void push(T item) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPush(item);
        notifyAll();
    }

    @Override
    public synchronized T pop() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T t = doPop();
        notifyAll();
        return t;
    }
}
