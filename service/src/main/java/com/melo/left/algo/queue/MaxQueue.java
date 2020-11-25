package com.melo.left.algo.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MaxQueue {

    private Deque<Integer> deque;
    Queue<Integer> q;
    Deque<Integer> d;

    public MaxQueue() {
        q = new LinkedList<Integer>();
        d = new LinkedList<Integer>();
    }

    public int max_value() {
        if (d.isEmpty()) {
            return -1;
        }
        return d.peekFirst();
    }

    public void push_back(int value) {
        while (!d.isEmpty() && d.peekLast() < value) {
            d.pollLast();
        }
        d.offer(value);
        q.offer(value);
    }

    public int pop_front() {
        if (q.isEmpty()) {
            return -1;
        }
        int ans = q.poll();
        if (ans == d.peekFirst()) {
            d.pollFirst();
        }
        return ans;
    }

    public static void main(String[] args) {
        MaxQueue queue = new MaxQueue();
        queue.push_back(7);
        queue.push_back(5);
        queue.push_back(3);
        queue.push_back(2);
        queue.push_back(8);
        System.out.println(queue.pop_front());
    }
}