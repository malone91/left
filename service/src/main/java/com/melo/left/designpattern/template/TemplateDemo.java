package com.melo.left.designpattern.template;

public class TemplateDemo {

    public static void main(String[] args) {
        AbstractClass abstractClass1 = new SubClassOne();
        abstractClass1.templateMethod();
        AbstractClass abstractClass2 = new SubClassTwo();
        abstractClass2.templateMethod();
    }
}