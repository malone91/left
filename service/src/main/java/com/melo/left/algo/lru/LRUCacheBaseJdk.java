package com.melo.left.algo.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheBaseJdk extends LinkedHashMap<Integer, Integer> {

    private Integer capacity;

    public LRUCacheBaseJdk(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheBaseJdk lruCache = new LRUCacheBaseJdk(2);
        lruCache.put(1, 1);
        System.out.println(lruCache);
        lruCache.put(2, 2);
        System.out.println(lruCache);
        lruCache.get(1);
        System.out.println(lruCache);
        lruCache.put(3, 3);
        System.out.println(lruCache);
        lruCache.get(1);
        System.out.println(lruCache);
//        {1=1}
//        {1=1, 2=2}
//        {2=2, 1=1}
//        {1=1, 3=3}
//        {3=3, 1=1}
    }
}
