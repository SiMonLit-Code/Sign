package com.sign.service.Impl;

import com.sign.dao.AdminDao;
import com.sign.entity.Admin;
import com.sign.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements IAdminService {

    @Resource
    AdminDao adminDao;
    @Override
    public Integer updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }

    @Override
    public Admin findAdmin() {
        return adminDao.findAdmin();
    }
}
