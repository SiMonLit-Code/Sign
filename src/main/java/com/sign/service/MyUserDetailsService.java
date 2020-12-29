package com.sign.service;

import com.sign.dao.RegisterDao;
import com.sign.entity.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 16:12:00
 * @description :
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private RegisterDao registerDao;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Register register = registerDao.registerFindById(account);
        if (null == register){
            return null;
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(register.getRole());
        return new User(register.getAccount(),register.getPwd(), Collections.singletonList(authority));
    }
}
