package com.sign.service;

import com.sign.entity.Admin;
import com.sign.entity.User;

public interface IAdminService {
    Integer updateAdmin(User admin);

    User findAdmin(String username);
}
