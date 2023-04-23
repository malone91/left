package com.melo.left.dubbospi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("melo")
public interface MyDubbo {

    @Adaptive
    void test(URL url);
}
