package com.sign.service;

import com.sign.entity.Admin;

public interface IAdminService {
    public Integer updateAdmin(Admin admin);

    public Admin findAdmin();
}
