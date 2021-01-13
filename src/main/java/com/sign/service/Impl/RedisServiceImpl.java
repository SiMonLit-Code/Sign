package com.sign.service.Impl;

import com.sign.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-12 11:38:00
 * @description :
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.tokenKeyPrefix}")
    private String prefix;

    /**
     * 在redis保存token
     * @param username
     * @param token
     * @return
     */
    @Override
    public void saveToken(String username, String token) {

        redisTemplate.opsForValue().set(prefix+username,token,1800, TimeUnit.SECONDS);
    }

    /**
     * 从Redis删除Token
     * @param username
     */
    @Override
    public void deleteToken(String username) {
        redisTemplate.delete(prefix+username);
    }

    /**
     * redis中的token是否失效
     * @param username
     * @param token
     * @return
     */
    @Override
    public boolean validateAccessToken(String username, String token) {
        return Objects.equals(redisTemplate.opsForValue().get(prefix+username), token);
    }

}
