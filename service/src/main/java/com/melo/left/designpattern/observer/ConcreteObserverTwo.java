package com.melo.left.designpattern.observer;

public class ConcreteObserverTwo implements Observer {
    @Override
    public void update(String message) {
        System.out.println("two update " + message);
    }
}
