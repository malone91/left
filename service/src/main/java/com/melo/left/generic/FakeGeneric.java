package com.melo.left.generic;

import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Java是伪泛型，C#是真泛型真实存在，List与List就是两个完全不同的类型，在系统运行期生成，被称为类型膨胀，
 * 在处理的时候生成新的类，List<String>与List<Integer>就是两个不同的类
 * Java的泛型在源码中存在，在字节码中不存在，在编译层实现，虚拟机层面就像完全没有实现泛型一样，从虚拟机视角来说，List与List没有区别
 * 泛型编写：安全性 可读性
 * 类型擦除 PECS法则
 * 类型擦除以后，泛型变量默认使用Object，这也就倒退到1.5之前没有泛型的实现方式，泛型的实现都是假的，jvm什么都没做
 *
 * 当然类型擦除之后泛型变量不一定是Object，比如public class Test<T extends Serializable>
 *
 * PECS法则：
 *  从集合中读取类型T的数据，且不能写入，用extends Producer Extends
 *  从集合中写入类型T的数据，且不需要读取，用super Consumer Super
 *
 *  Integer Float的最小公倍数是Number
 */
public class FakeGeneric {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Integer> list = Lists.newArrayList();
        List<String> strings = Lists.newArrayList();
        //true
        System.out.println(list.getClass() == strings.getClass());
        //编译器在编译时会去找可能出错的地方，但无法保证100%运行
        List<Integer> l1 = Lists.newArrayList();
        l1.getClass().getMethod("add", Object.class).invoke(l1, "a");
        //Integer在编译之后被擦除了，只保留了原始类型
        System.out.println(l1);
        //diamond配置平台 导致程序异常 l3.add(1)
        List<Long> l3 = Lists.newArrayList();
        System.out.println(l3);

        //PECS
        List<? extends Object> l4 = new ArrayList<>();
        //编译器只知道list是Object的某个子类的list，不知道是哪个子类，任何类型无法加入
        //因为list存入的是Object的子类型，因此总可以从中读取Object对象，比如list.get(0)被允许
//        l4.add(1); wrong 只能放入Integer和它的子类型
        List<? super Integer> l5 = new ArrayList<>();
        l5.add(1);
//        l5.add(1.2); wrong
        Object o = l5.get(0);
        System.out.println(o);
    }
}
