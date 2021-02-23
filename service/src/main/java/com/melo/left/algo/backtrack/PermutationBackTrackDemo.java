package com.melo.left.algo.backtrack;

import java.util.LinkedList;
import java.util.List;

/**
 * 回溯算法解决全排列问题
 */
public class PermutationBackTrackDemo {

    static List<List<Integer>> res = new LinkedList<>();

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<List<Integer>> subSet = getPermutation(arr);
        System.out.println(subSet);
    }

    private static List<List<Integer>> getPermutation(int[] arr) {
        LinkedList<Integer> track = new LinkedList<>();
        backTrack(arr, track);
        return res;
    }

    private static void backTrack(int[] arr,LinkedList<Integer> track) {
        if (track.size() == arr.length) {
            System.out.println(track);
            res.add(new LinkedList<>(track));
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            //排除不合法的选择
            if (track.contains(arr[i])) {
                continue;
            }
            track.add(arr[i]);
            backTrack(arr, track);
            track.removeLast();
        }
    }
}
