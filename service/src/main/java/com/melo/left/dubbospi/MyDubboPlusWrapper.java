package com.melo.left.dubbospi;

import com.alibaba.dubbo.common.URL;

public class MyDubboPlusWrapper implements MyDubbo{

    private final MyDubbo myDubbo;

    public MyDubboPlusWrapper(MyDubbo myDubbo) {
        this.myDubbo = myDubbo;
    }

    @Override
    public void test(URL url) {
        System.out.println("wrapper 2 start");
        myDubbo.test(url);
        System.out.println("wrapper 2 end");

    }
}
