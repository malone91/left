package com.melo.left.designpattern.observer;

/**
 * register 函数还可以叫作 attach，remove 函数还可以叫作 detach
 * 如果注册成功之后需要执行的后续操作越来越多，
 * 那用户注册的 register() 函数的逻辑会变得越来越复杂，
 * 也就影响到代码的可读性和可维护性。
 */
public class OberverDemo {

    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());
        subject.notifyObservers("melo");
    }
}
