package com.melo.left.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 吞吐量定义为每秒消化的pig的数量
 * -Xms2g -Xmx2g  CMS清理老年代：-XX:+UseConcMarkSweepGC 并行回收器清理新生代：-XX:+UseParNewGC
 * 将堆的256M分配给新生代： -Xmn256m  Eden和Survivor区设置为大小一样
 *
 * 新生代和老年代都使用ParallelGC: -XX:+UseParallelGC 堆的1g给新生代 -Xmn1g
 */
public class ThroughputDemo {

    static volatile List pigs = new ArrayList<>();
    static volatile int eatenPigs = 0;
    static final int total_pig = 5000;

    public static void main(String[] args) {
        new PigEater().start();
        new PigDigeter().start();
    }

    static class PigEater extends Thread {
        @Override
        public void run() {
            while (true) {
                pigs.add(new byte[32 * 1024 * 1024]);
                if (eatenPigs > total_pig) {
                    return;
                }
                //吞吐量每秒可达50头/秒
                takeNap(20);
            }
        }
    }

    static class PigDigeter extends Thread {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                takeNap(100);
                eatenPigs += pigs.size();
                pigs = new ArrayList<>();
                if (eatenPigs > total_pig) {
                    System.out.println(System.currentTimeMillis() - start);
                    return;
                }
            }
        }
    }

    static void takeNap(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
