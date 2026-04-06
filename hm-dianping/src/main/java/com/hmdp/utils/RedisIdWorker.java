package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisIdWorker {

    private static final long BEGIN_TIMESTAMP = 1767225600L;
    private StringRedisTemplate stringRedisTemplate;

    public  long nextId(String keyPrefix) { // 生成全局id（购买商品的id）
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd")); // 拼接日期可以防止当天下单的商品多余32位的最大值
        // 2.2.自增长
        long count = stringRedisTemplate.opsForValue().increment("icr" + keyPrefix + ":" + date);

        // 3.拼接并返回
        return timestamp << 32 | count; // 假设序列号位32位
    }

}
