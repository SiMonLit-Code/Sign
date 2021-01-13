package com.sign.service;

import com.sign.entity.User;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-12 11:37:00
 * @description :
 */
public interface IRedisService {
    void saveToken(String username,String token);
    void deleteToken(String username);
    boolean validateAccessToken(String username, String token);
}
