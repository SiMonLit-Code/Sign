package com.sign.service;

import com.sign.dao.RegisterDao;
import com.sign.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = registerDao.registerFindById(username);
        if (null == user){
            return null;
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }
}
