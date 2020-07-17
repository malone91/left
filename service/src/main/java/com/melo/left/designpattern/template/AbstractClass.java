package com.melo.left.designpattern.template;

/**
 * Define the skeleton of an algorithm in an operation, deferring some steps to subclasses.
 * Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm’s structure.
 * 模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。
 * 模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。
 *
 * 把一个算法中不变的流程抽象到父类的模板方法 templateMethod() 中，
 * 将可变的部分 method1()、method2() 留给子类 ContreteClass1 和 ContreteClass2 来实现。
 * 所有的子类都可以复用父类中模板方法定义的流程代码。
 */
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