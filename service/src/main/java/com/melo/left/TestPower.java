package com.melo.left;

public class TestPower {

    public static void main(String[] args) {
        System.out.println(pow(2, -3));
    }

    public static double pow(int x, int y) {
        if (x == 0) {
            return 0;
        }
        if (y == 0) {
            return 1;
        }
        boolean negative = y < 0 ? true : false;
        if (negative) {
            y = -y;
        }
        double result = x;
        for (int i = 1; i < y; i++) {
            result = result * x;
        }
        if (negative) {
            result = 1 / result;
        }
        return result;
    }


    private static volatile TestPower instance;

    private static TestPower getInstance() {
        if (instance == null) {
            synchronized (TestPower.class) {
                if (instance == null) {
                    instance = new TestPower();
                }
                return instance;
            }
        } else {
            return instance;
        }
    }

}
