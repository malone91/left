package com.melo.left.designpattern.template;

public abstract class AbstractClass {

    /**
     * sub class can't override because of final description
     */
    public final void templateMethod() {
        System.out.println("父类方法开始");
        method1();
        method2();
        System.out.println("父类方法结束");
    }


    /**
     * sub class must override because of abstract description
     */
    protected abstract void method1();

    protected abstract void method2();
 }