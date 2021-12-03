package com.melo.left.training.java;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Class<?> hello = new HelloLoader().findClass("Hello");
        Object instance = hello.newInstance();
        Method method = hello.getMethod("hello");
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] content = getContent("F:/code/left/left/service/src/main/java/com/melo/left/training/java/Hello.xlass");
            byte[] helloBytes = new byte[content.length];
            for (int i = 0; i < content.length; i++) {
                helloBytes[i] = (byte) (255 - content[i]);
            }
            //载入
            return defineClass(name, helloBytes, 0, helloBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }

    public byte[] getContent(String path) throws IOException {
        File file = new File(path);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            System.out.println("file is too big...");
            return null;
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[(int) length];
        int offset = 0;
        int numRead;
        while (offset < buffer.length && (numRead = inputStream.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        inputStream.close();
        return buffer;
    }
}
