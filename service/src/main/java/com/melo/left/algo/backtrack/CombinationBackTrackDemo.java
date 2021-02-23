package com.melo.left.algo.backtrack;

import java.util.LinkedList;
import java.util.List;

/**
 * 回溯算法解决组合问题
 */
public class CombinationBackTrackDemo {

    static List<List<Integer>> res = new LinkedList<>();

    public static void main(String[] args) {
        List<List<Integer>> combination = getCombination(4, 2);
        System.out.println(combination);
    }

    private static List<List<Integer>> getCombination(int n, int k) {
        if (k <= 0 || n <= 0) {
            return res;
        }
        LinkedList<Integer> track = new LinkedList<>();
        backTrack(n, k, 1, track);
        return res;
    }

    private static void backTrack(int n, int k, int start, LinkedList<Integer> track) {
        if (k == track.size()) {
            System.out.println(track);
            res.add(new LinkedList<>(track));
            return;
        }
        for (int i = start; i <= n; i++) {
            track.add(i);
            backTrack(n, k, i + 1, track);
            track.removeLast();
        }
    }
}
