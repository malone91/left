package com.melo.left.controller;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("zk")
public class ZkController {

    @Autowired
    private CuratorFramework curatorFramework;

    @GetMapping("bad/countdown")
    public String badCountDown() {
        final CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    System.err.println(sdf.format(new Date()));
                }
            }).start();
        }
        latch.countDown();
        return "done";
    }

    @GetMapping("good/countdown")
    public String goodCountDown() {
        final InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/zk");
        final CountDownLatch latch = new CountDownLatch(1);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        lock.acquire();
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    System.err.println(sdf.format(new Date()));

                    try {
                        lock.release();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }
        latch.countDown();
        return "done";
    }
}
