package com.melo.left.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("zk")
public class ZkController {

    @Autowired
    private CuratorFramework curatorFramework;

    @ApiOperation("由于并发原因有问题的id生成器")
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
                        log.error(e.getMessage());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    log.info(sdf.format(new Date()));
                }
            }).start();
        }
        latch.countDown();
        return "done";
    }

    @ApiOperation("实现分布式锁")
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
                        log.error(e.getMessage());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    log.info(sdf.format(new Date()));

                    try {
                        lock.release();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }).start();
        }
        latch.countDown();
        return "done";
    }

    @ApiOperation("分布式计数器")
    @GetMapping("count")
    public Boolean count() throws Exception {
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(
                curatorFramework, "/atomic_path", new RetryNTimes(3, 1000));
        AtomicValue<Integer> atomicValue = atomicInteger.add(8);
        log.info(atomicValue.toString());
        return atomicValue.succeeded();
    }

    @ApiOperation("JDK原生赛跑")
    @GetMapping("jdk/cyclic/barrier")
    public boolean cyclicBarrier() {
        int runnerCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(runnerCount);
        ExecutorService executorService = Executors.newFixedThreadPool(runnerCount);
        for (int i = 0; i < runnerCount; i++) {
            executorService.submit(new Thread(new Runner(i + "号选手", barrier)));
        }
        executorService.shutdown();
        return true;
    }

    class Runner implements Runnable {

        private String name;
        private CyclicBarrier barrier;
        public Runner(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            log.info(name + "准备好了~~~");
            try {
                barrier.await();
            } catch (InterruptedException |BrokenBarrierException e) {
                log.error(e.getMessage());
            }
            log.info(name + "起跑！！！");
        }
    }

    @ApiOperation("分布式赛跑")
    @GetMapping("distribute/cyclic/barrier")
    public boolean zkCyclicBarrier() throws Exception {
        int runnerCount = 3;
        DistributedBarrier barrier = new DistributedBarrier(curatorFramework, "/barrier_path");
        for (int i = 0; i < runnerCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName() + "号设置");
                    try {
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        log.info(Thread.currentThread().getName() + "启动");
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(2);
        barrier.removeBarrier();
        return true;
    }
}
