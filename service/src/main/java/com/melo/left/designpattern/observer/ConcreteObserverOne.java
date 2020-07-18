package com.melo.left.designpattern.observer;

public class ConcreteObserverOne implements Observer {
    @Override
    public void update(String message) {
        System.out.println("one update " + message);
    }
}
