package com.melo.left.algo.dynamic;

import java.util.Arrays;

public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String s1 = "abddfa";
        String s2 = "agfddefa";
        System.out.println(longestCommonSubsequence(s1, s2));
    }

    private static int longestCommonSubsequence(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], 0);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[m][n];
    }
}