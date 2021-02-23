package com.melo.left.algo.backtrack;

import java.util.LinkedList;
import java.util.List;

/**
 * 回溯算法解决子集合问题
 */
public class SubSetBackTrackDemo {

    static List<List<Integer>> res = new LinkedList<>();

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<List<Integer>> subSet = getSubSet(arr);
        System.out.println(subSet);
    }

    private static List<List<Integer>> getSubSet(int[] arr) {
        LinkedList<Integer> track = new LinkedList<>();
        backTrack(arr, 0, track);
        return res;
    }

    private static void backTrack(int[] arr, int start,LinkedList<Integer> track) {
        System.out.println(track);
        res.add(new LinkedList<>(track));
        for (int i = start; i < arr.length; i++) {
            track.add(arr[i]);
            backTrack(arr, i + 1, track);
            track.removeLast();
        }
    }
}
