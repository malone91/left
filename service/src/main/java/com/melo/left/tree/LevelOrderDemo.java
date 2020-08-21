package com.melo.left.tree;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderDemo {

    public void layerIterator(Node root) {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            System.out.println("current value " + currNode.value);
            if (currNode.left != null) {
                queue.add(currNode.left);
            }
            if (currNode.right != null) {
                queue.add(currNode.right);
            }
        }
    }

    @Data
    public class Node {
        private int value;
        private Node left;
        private Node right;
    }
}