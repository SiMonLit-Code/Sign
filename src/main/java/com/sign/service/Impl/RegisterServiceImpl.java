package com.sign.service.Impl;

import com.sign.dao.RegisterDao;
import com.sign.entity.User;
import com.sign.jwt.JwtTokenUtils;
import com.sign.service.IRegisterService;
import com.sign.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Resource
    private RegisterDao registerDao;
    @Autowired
    private MyUserDetailsService detailsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${jwt.validate}")
    private long validate;
    @Override
    public boolean registerInsert(User user) {
        return registerDao.registerInsert(user);
    }

    @Override
    public boolean registerFind(User user) {
        //校验
        UserDetails userDetails = detailsService.loadUserByUsername(user.getUsername());
        if (Objects.isNull(userDetails)){
            return false;
        }
        //生成token
        String token = JwtTokenUtils.generateToken(user.getPassword());
        //token存入redis
        redisTemplate.opsForValue().set(user.getPassword(), token,validate * 1000, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Integer registerFindAc(User user) {
        return registerDao.registerFindAc(user);
    }

    @Override
    public List<User> registerFindAll() {
        return registerDao.registerFindAll();
    }

    @Override
    public User registerFindById(String account) {
        return registerDao.registerFindById(account);
    }

    @Override
    public Integer registerUpdate(User user) {
        return registerDao.registerFindUpdate(user);
    }


}
