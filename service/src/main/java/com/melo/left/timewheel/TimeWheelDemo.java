package com.melo.left.timewheel;

import io.netty.util.HashedWheelTimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeWheelDemo {

    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //时间轮步长300ms
        HashedWheelTimer timer = new HashedWheelTimer(300, TimeUnit.MILLISECONDS);
        System.out.println("start: " + LocalDateTime.now().format(formatter));
        timer.newTimeout(timeout -> {
            System.out.println("task: " + LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
    }
}
