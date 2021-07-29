package com.melo.left.limiter;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelByStarterDemo {

    @GetMapping
    @SentinelResource("hello")
    public String hello() {
        return "hello melo, this is sentinel";
    }
}
