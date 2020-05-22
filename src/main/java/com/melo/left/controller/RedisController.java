package com.melo.left.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.melo.left.model.ProgramLanguage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("redis")
public class RedisController {

    private static final TypeReference<List<ProgramLanguage>> TYPE_REFERENCE = new TypeReference<List<ProgramLanguage>>(){};

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

    @PostMapping("set/list")
    public String setList(String key) {
        List<ProgramLanguage> values = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ProgramLanguage language = new ProgramLanguage();
            language.setId(1);
            language.setType("Java" + i);
            language.setDesc("Love" + i);
            values.add(language);
        }
        redisTemplate.opsForValue().set(key, values);
        log.info("serializer is {}", redisTemplate.getValueSerializer());
        return "success";
    }

    @GetMapping("get/list")
    public List<ProgramLanguage> getList(@RequestParam("key") String key) {
        Object value = redisTemplate.opsForValue().get(key);
        log.info("serializer is {}", redisTemplate.getValueSerializer());
        return (List<ProgramLanguage>)value;
    }
}
