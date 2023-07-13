package com.melo.gateway;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CglibTest implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return JSON.toJSONString(objects) + "proxy";
    }

    @Test
    public void testCglib() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InterfaceMaker maker = new InterfaceMaker();
        maker.add(new Signature("sayHi", Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
        Class<?> interfaceClass = maker.create();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[]{interfaceClass});
        enhancer.setCallback(this);
        Object obj = enhancer.create();

        Method method = obj.getClass().getMethod("sayHi", String.class);
        Object result = method.invoke(obj, "hi melo");
        System.out.println(result);
    }
}
