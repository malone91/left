package com.melo.left.algo.dynamic;

public class HuiWenDemo {

    public static void main(String[] args) {
        HuiWenDemo demo = new HuiWenDemo();
        System.out.println(demo.deduceHuiWen("fffffs"));
    }

    public boolean deduceHuiWen(String s) {
        if (s.length() < 2) {
            return false;
        }
        char[] chars = s.toCharArray();
        int length = chars.length;
        int middle = length >> 1;
        int left = 0, right = length - 1;
        while (left < middle) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}