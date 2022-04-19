package com.aiccj.abtest.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Service
public class CacheService {


    public static final Long DEFAULT_TIMEOUT = 30 * 60 * 1000L;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    public static final Long A_HOUR = 60 * 60 * 1000L;
    public static final Long A_DAY = 24 * 60 * 60 * 1000L;
    public static final Long A_WEEK = 7 * 24 * 60 * 60 * 1000L;
    public static final Long A_MONTH = 30 * 24 * 60 * 60 * 1000L;


    @Resource
    RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    
    public void set(String key, String value, int time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public void set(String key, String value) {
        set(key, value, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    public Boolean remove(String key) {
        return redisTemplate.delete(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        String _o = get(key);
        if (null == _o) {
            return defaultValue;
        }
        return _o;
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void expire(String key, int expireTime, TimeUnit minutes) {
        redisTemplate.expire(key, expireTime, minutes);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
