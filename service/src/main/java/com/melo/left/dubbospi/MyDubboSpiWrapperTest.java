package com.melo.left.dubbospi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class MyDubboSpiWrapperTest {

    public static void main(String[] args) {
        ExtensionLoader<MyDubbo> extensionLoader = ExtensionLoader.getExtensionLoader(MyDubbo.class);
        MyDubbo adaptiveExtension = extensionLoader.getAdaptiveExtension();
        adaptiveExtension.test(URL.valueOf("melo://127.0.0.1"));
    }
}
