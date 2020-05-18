package com.melo.left.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("set")
    public String set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return "success";
    }

    @GetMapping("get")
    public String get(@RequestParam("key") String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return (String)value;
    }
}
