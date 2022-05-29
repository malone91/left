package com.melo.left.algo.slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class ContainsSubStr {

    public static void main(String[] args) {
        System.out.println(sliding("fdfsdfdf","ff"));
    }

    private static String sliding(String s, String t) {
        if (s == null || t == null) {
            return null;
        }
        Map<Character, Integer> window = new HashMap<>();
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        Map<Character, Integer> need = new HashMap<>(tArr.length);
        for (char c : tArr) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        //开始滑动窗口
        int left = 0;
        int right = 0;
        int valid = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;
        while (right < sArr.length) {
            //移入窗口的字符
            char c = sArr[right];
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c) == window.get(c)) {
                    //包含字符的个数都满足了，如果target串有重复的字符串也要包含进去
                    valid++;
                }
            }

            //是否需要shrink
            while (valid == need.size()) {
                //上面可行解找到了，现在搞最优解
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                //移出的字符串
                char d = sArr[left];
                left++;
                //窗口内字符串进行更新
                if (need.containsKey(d)) {
                    if (need.get(d) == window.get(d)) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        if (len == Integer.MAX_VALUE) {
            return "";
        }
        String result = "";
        for (int i = 0; i < len; i++) {
            result += sArr[start + i];
        }
        return result;
    }

}
