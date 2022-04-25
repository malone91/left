package com.melo.left.condition.condition;

public abstract class MyStack<T> {
    private Object[] items;
    /**
     * 下一个要插入元素的索引
     */
    private int index;

    /**
     * 如果只有有参构造函数，子类必须调用该构造方法
     * @param capacity
     */
    public MyStack(int capacity) {
        items = new Object[capacity];
    }

    protected void doPush(T item) {
        items[index++] = item;
    }

    protected boolean isFull() {
        return index == items.length;
    }

    protected T doPop() {
        T t = (T) items[--index];
        return t;
    }

    protected boolean isEmpty() {
        return index == 0;
    }

    public abstract void push(T item) throws InterruptedException;

    public abstract T pop() throws InterruptedException;
}
