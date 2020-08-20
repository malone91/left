package com.melo.left.algo.array;

import java.util.Arrays;

public class ZeroMovementDemo {

    public static void main(String[] args) {
        int[] arr = {3, 0, 4, 5, 0, 9, 7};
        moveZeros(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }

    private static void moveZeros(int[] a, int n) {
        //下一个非0索引
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                a[j] = a[i];
                if (i != j) {
                    a[i] = 0;
                }
                j++;
            }
        }
    }
}