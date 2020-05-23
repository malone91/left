package com.melo.left.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("thread/pool")
public class ThreadPoolController {

    @Autowired
    private ExecutorService myThreadPoolExecutor;

    @GetMapping("async")
    public String asyncSyncParalled() {
        Future<Integer> future = myThreadPoolExecutor.submit(
                //模拟异步查询数据库
                () -> spoofQueryDb(3)
        );
        try {
            Integer integer = future.get();
            log.info("spoofQueryDb {}", integer);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return "done";
    }

    private Integer spoofQueryDb(int i) {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {

        }
        return i;
    }
}
