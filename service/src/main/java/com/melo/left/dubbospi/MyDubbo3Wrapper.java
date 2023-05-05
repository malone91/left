package com.melo.left.dubbospi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Wrapper;

@Wrapper(matches = {"melo"})
public class MyDubbo3Wrapper implements MyDubbo{

    private final MyDubbo myDubbo;

    public MyDubbo3Wrapper(MyDubbo myDubbo) {
        this.myDubbo = myDubbo;
    }

    @Override
    public void test(URL url) {
        System.out.println("wrapper 3 start");
        myDubbo.test(url);
        System.out.println("wrapper 3 end");

    }
}
