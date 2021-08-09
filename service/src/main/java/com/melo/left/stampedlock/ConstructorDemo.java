package com.melo.left.stampedlock;

public class ConstructorDemo {

    public ConstructorDemo() {
        ConstructorDemo demo = new ConstructorDemo();
        System.out.println(demo);
    }

    public static void main(String[] args) {
        ConstructorDemo demo = new ConstructorDemo();
        System.out.println(demo);
    }
}
