package com.melo.left.spi;

/**
 * Service Provider Interface，是一种服务发现机制。
 * 它通过在ClassPath路径下的META-INF/services文件夹查找文件，
 * 自动加载文件里所定义的类。
 * 这一机制为很多框架扩展提供了可能，比如在Dubbo、JDBC中都使用到了SPI机制
 */
public interface SpiService {

    void execute();
}
