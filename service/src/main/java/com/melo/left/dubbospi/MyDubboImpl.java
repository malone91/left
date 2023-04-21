package com.melo.left.dubbospi;

import com.alibaba.dubbo.common.URL;

public class MyDubboImpl implements MyDubbo{

    @Override
    public void test(URL url) {
        System.out.println(url);
    }
}
