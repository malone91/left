package com.melo.left.algo.dynamic;

public class HuiWenCenterExpandDemo {

    public static void main(String[] args) {
        String s = "fsdfd";
        System.out.println(longestHuiWen(s));
    }

    private static String longestHuiWen(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        char[] chars = s.toCharArray();
        int begin = 0;
        int maxLen = 1;

        for (int i = 0; i < len - 1; i++) {
            int oddLen = expandAroundCenter(chars, i, i);
            int evenLen = expandAroundCenter(chars, i, i + 1);
            int currMaxLen = Math.max(oddLen, evenLen);
            if (currMaxLen > maxLen) {
                maxLen = currMaxLen;
                begin = i - (maxLen - 1) / 2;
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    private static int expandAroundCenter(char[] chars, int left, int right) {
        int len = chars.length;
        int i = left;
        int j = right;
        while (i >= 0 && j < len) {
            if (chars[i] == chars[j]) {
                i--;
                j++;
            } else {
                break;
            }
        }
        //j - i + 1 - 2
        return j - i - 1;
    }
}
