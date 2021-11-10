package com.melo.left.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * https://www.jianshu.com/p/3a3edbcd8f24
 * 可以通过ServiceLoader.load或者Service.providers方法拿到实现类的实例
 */
public class SpiDemo {

    public static void main(String[] args) {
        Iterator<SpiService> providers = Service.providers(SpiService.class);
        while (providers.hasNext()) {
            SpiService spiService = providers.next();
            spiService.execute();
        }

        ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> iterator = load.iterator();
        while (iterator.hasNext()) {
            SpiService next = iterator.next();
            next.execute();
        }
    }
}
