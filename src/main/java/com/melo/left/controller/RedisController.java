package com.melo.left.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("set")
    public String set() {
        redisTemplate.opsForValue().set("melo", "haha");
        return "success";
    }

    @GetMapping("get")
    public String get() {
        Object value = redisTemplate.opsForValue().get("melo");
        return (String)value;
    }
}
