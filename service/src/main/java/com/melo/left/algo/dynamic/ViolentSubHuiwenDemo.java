package com.melo.left.algo.dynamic;

public class ViolentSubHuiwenDemo {

    public static void main(String[] args) {
        String str = "fsfsfdfds";
        System.out.println(longestSubHuiWen(str));
    }

    private static String longestSubHuiWen(String str) {
        int len = str.length();
        if (len < 2) {
            return str;
        }
        char[] chars = str.toCharArray();
        int begin = 0;
        int maxLen = 1;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && deduceHuiWen(chars, i, jgi)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return str.substring(begin, begin + maxLen);
    }

    /**
     * 判断是否是回文串
     * @param chars
     * @param left
     * @param right
     * @return
     */
    private static boolean deduceHuiWen(char[] chars, int left, int right) {
        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
