package com.melo.left.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 java -jar -Xms1000m -Xmx4000m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.hprof  -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:/tmp/heapTest.log heapTest-0.0.1-SNAPSHOT.jar
 */
@Slf4j
@RestController
@RequestMapping("/oom")
public class OomTestController {

    @GetMapping("/test")
    public String test() {
        ThreadLocal<Byte[]> threadLocal = new ThreadLocal<>();
        threadLocal.set(new Byte[1024 * 4096]);
        log.info("set success");
        return "success";
    }
}
