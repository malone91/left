package com.melo.gatewaycenter.domain.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void pushMessage(String topic, Object message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
