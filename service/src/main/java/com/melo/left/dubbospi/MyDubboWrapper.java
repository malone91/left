package com.melo.left.dubbospi;

import com.alibaba.dubbo.common.URL;

public class MyDubboWrapper implements MyDubbo{

    private final MyDubbo myDubbo;

    public MyDubboWrapper(MyDubbo myDubbo) {
        this.myDubbo = myDubbo;
    }

    @Override
    public void test(URL url) {
        System.out.println("wrapper 1 start");
        myDubbo.test(url);
        System.out.println("wrapper 1 end");

    }
}
