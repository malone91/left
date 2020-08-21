package com.melo.left.algo.array;

import java.util.HashMap;
import java.util.Map;

public class LRUBasedArray<T> {

    public static void main(String[] args) {
        LRUBasedArray<Integer> lruCache = new LRUBasedArray<>();
        lruCache.offer(3);
        lruCache.offer(2);
        lruCache.offer(4);
        lruCache.offer(1);
        System.out.println(lruCache.toString());
        lruCache.offer(1);
        lruCache.offer(2);
        lruCache.offer(5);
        System.out.println(lruCache.toString());
    }

    private static final int DEFAULT_CAPACITY = 1 << 3;

    private int capacity;
    private int count;
    private T[] value;
    private Map<T, Integer> holder;

    public LRUBasedArray(int capacity) {
        this.capacity = capacity;
        this.count = 0;
        value = (T[]) new Object[capacity];
        holder = new HashMap<>();
    }

    public LRUBasedArray() {
        this(DEFAULT_CAPACITY);
    }

    public void offer(T object) {
        if (object == null) {
            throw new IllegalArgumentException("object can't be null");
        }
        Integer index = holder.get(object);
        if (index == null) {
            if (isFull()) {
                removeAndCache(object);
            } else {
                cache(object);
            }
        } else {
            update(index);
        }
    }

    public boolean isContain(T object) {
        return holder.containsKey(object);
    }

    private void cache(T object) {
        rightMove(count);
        value[0] = object;
        holder.put(object, 0);
        count++;
    }

    private void update(int index) {
        T object = value[index];
        rightMove(index);
        value[0] = object;
        holder.put(object, 0);
    }

    private void removeAndCache(T object) {
        T key = value[--count];
        holder.remove(key);
        cache(object);
    }

    private void rightMove(int end) {
        for (int i = end - 1; i >= 0; i--) {
            value[i + 1] = value[i];
            holder.put(value[i], i + 1);
        }
    }

    private boolean isFull() {
        return capacity == count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]).append(" ");
        }
        return sb.toString();
    }
}