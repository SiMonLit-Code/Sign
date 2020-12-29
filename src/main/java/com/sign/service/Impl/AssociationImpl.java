package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.service.IAssociationService;

import javax.annotation.Resource;
import java.util.List;

public class AssociationImpl implements IAssociationService {

    @Resource
    SignUpDao signUpDao;

    @Override
    public List<Add> associationFind() {
        return signUpDao.associationFind();
    }
}
