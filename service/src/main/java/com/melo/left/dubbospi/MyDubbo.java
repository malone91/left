package com.melo.left.dubbospi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("melo")
public interface MyDubbo {

    @Adaptive
    void test(URL url);
}
