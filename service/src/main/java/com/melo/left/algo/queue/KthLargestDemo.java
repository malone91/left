package com.melo.left.algo.queue;

import java.util.PriorityQueue;

public class KthLargestDemo {

    public static void main(String[] args) {
        int[] a = {2, 4, 3, 2, 3, 4, 70, 89, 34};
        KthLargestDemo demo = new KthLargestDemo(3, a);
        System.out.println(demo.queue.peek());
    }

    final PriorityQueue<Integer> queue;
    final int k;

    public KthLargestDemo(int k, int[] a) {
        this.k = k;
        queue = new PriorityQueue<>(k);
        for (int n : a) {
            add(n);
        }
    }

    private int add(int n) {
        if (queue.size() < k) {
            queue.offer(n);
        } else if (queue.peek() < n) {
            queue.poll();
            queue.offer(n);
        }
        return queue.peek();
    }
}
