package com.melo.left.reflect;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Objects;

@Data
public class ClassDemo {

    private int a;
    private Integer b;

    public static void main(String[] args) {
        Method[] methods = ClassDemo.class.getMethods();
        for (Method method : methods) {
            if (Objects.equals(method.getName(), "setA")) {
                boolean aPrimitive = method.getParameterTypes()[0].isPrimitive();
                //true 参数是基本类型
                System.out.println("aPrimitive " + aPrimitive);
            }
            if (Objects.equals(method.getName(), "setB")) {
                boolean bPrimitive = method.getParameterTypes()[0].isPrimitive();
                //false 参数不是基本类型
                System.out.println("bPrimitive " + bPrimitive);
            }
        }
    }
}
