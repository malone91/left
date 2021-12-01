package com.melo.left.training.java;

import sun.misc.Launcher;

import java.net.URL;


public class ClassLoaderView {
    public static void main(String[] args) {
        //启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
    }

    public static void printClassLoader(String name, ClassLoader loader) {
        if (loader != null) {
            System.out.println(name + loader.toString());
        }
    }
}
