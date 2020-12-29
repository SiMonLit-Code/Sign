package com.sign.service.Impl;

import com.sign.dao.RegisterDao;
import com.sign.entity.Register;
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
    @Value("jwt.validate")
    private long validate;
    @Override
    public boolean registerInsert(Register register) {
        return registerDao.registerInsert(register);
    }

    @Override
    public boolean registerFind(Register register) {
        //校验
        UserDetails userDetails = detailsService.loadUserByUsername(register.getAccount());
        if (Objects.isNull(userDetails)){
            return false;
        }
        //生成token
        String token = JwtTokenUtils.generateToken(register.getAccount());
        //token存入redis
        redisTemplate.opsForValue().set(register.getAccount(), token,validate * 1000, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Integer registerFindAc(Register register) {
        return registerDao.registerFindAc(register);
    }

    @Override
    public List<Register> registerFindAll() {
        return registerDao.registerFindAll();
    }

    @Override
    public Register registerFindById(String account) {
        return registerDao.registerFindById(account);
    }

    @Override
    public Integer registerUpdate(Register register) {
        return registerDao.registerFindUpdate(register);
    }


}
