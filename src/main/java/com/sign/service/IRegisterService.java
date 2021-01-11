package com.sign.service;

import com.sign.entity.User;

import java.util.List;

public interface IRegisterService {
    boolean registerInsert(User user);

    boolean registerFind(User user);

    Integer registerFindAc(User user);

    List<User> registerFindAll();

    User registerFindById(String account);

    Integer registerUpdate(User user);
}
