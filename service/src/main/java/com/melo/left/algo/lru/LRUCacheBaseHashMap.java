package com.melo.left.algo.lru;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheBaseHashMap {

    private int capacity;
    private DoubleLinkedList doubleLinkedList;
    private Map<Integer, Node> map;

    public LRUCacheBaseHashMap(int capacity) {
        this.capacity = capacity;
        doubleLinkedList = new DoubleLinkedList();
        map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        doubleLinkedList.makeUsed(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node oldNode = map.get(key);
            oldNode.value = value;
            doubleLinkedList.makeUsed(oldNode);
            return;
        }

        if (capacity == doubleLinkedList.size) {
            Node node = doubleLinkedList.removeFirst();
            map.remove(node.key);
        }

        Node newNode = new Node(key, value);
        map.put(key, newNode);
        doubleLinkedList.addLast(newNode);
    }

    public static class Node {
        public int key, value;
        public Node pre, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class DoubleLinkedList {
        private int size;
        private Node head, tail;

        public DoubleLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.pre = head;
            size = 0;
        }

        public void addLast(Node node) {
            node.next = tail;
            node.pre = tail.pre;
            tail.pre.next = node;
            tail.pre = node;
            size++;
        }

        public void remove(Node node) {
            node.next.pre = node.pre;
            node.pre.next = node.next;
            size--;
        }

        public Node removeFirst() {
            if (head.next == tail) {
                return null;
            }
            Node firstNode = head.next;
            remove(firstNode);
            return firstNode;
        }

        public void makeUsed(Node node) {
            remove(node);
            addLast(node);
        }

        public int size() {
            return size;
        }
    }

}
