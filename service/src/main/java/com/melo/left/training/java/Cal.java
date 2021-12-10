package com.melo.left.training.java;

public class Cal {

    static abstract class Food {}
    static class Beef extends Food {}
    static class Egg extends Food {}

    public void eat(Food food) {
        System.out.println("eat food");
    }

    public void eat(Beef beef) {
        System.out.println("eat beef");
    }

    public void eat(Egg egg) {
        System.out.println("eat egg");
    }

    public static void main(String[] args) {
        Food beef = new Beef();
        Food egg = new Egg();
        Cal m = new Cal();
        m.eat(beef);
        m.eat(egg);
    }
}
