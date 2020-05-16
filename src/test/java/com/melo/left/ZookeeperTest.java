package com.melo.left;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperTest {

    @Test
    public void testCountDownLatch() {
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

        try {
            TimeUnit.SECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
