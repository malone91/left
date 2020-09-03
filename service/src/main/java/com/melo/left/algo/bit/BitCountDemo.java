package com.melo.left.algo.bit;

public class BitCountDemo {

    public static void main(String[] args) {
        int int_32 = 23;

        int count = 0;
        while (int_32 != 0) {
            int_32 = int_32 & (int_32 - 1);
            count++;
        }
        System.out.println(count);
    }
}
