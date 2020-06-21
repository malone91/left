package com.melo.left.function;

import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {
        Function<Integer, Integer> plus = i -> i + 3;
        Function<Integer, Integer> multi = i -> i * 6;
        //5
        System.out.println(plus.apply(2));
        //?
        System.out.println(multi.compose(plus).apply(3));
        System.out.println(multi.andThen(plus).apply(5));
    }

}
