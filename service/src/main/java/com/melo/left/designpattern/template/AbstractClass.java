package com.melo.left.designpattern.template;

public abstract class AbstractClass {

    public final void templateMethod() {
        System.out.println("父类方法开始");
        method1();
        method2();
        System.out.println("父类方法结束");
    }

    protected abstract void method1();

    protected abstract void method2();
 }