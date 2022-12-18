package com.example.stock.repository;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;


    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(Long key) {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(generate(key), "lock", Duration.ofMillis(3_000));
    }

    private String generate(Long key) {
        return key.toString();
    }

    public Boolean unlock(Long key) {
        return redisTemplate.delete(generate(key));
    }
}
