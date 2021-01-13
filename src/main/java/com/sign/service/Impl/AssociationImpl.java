package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.RegistrationFormAddition;
import com.sign.service.IAssociationService;

import javax.annotation.Resource;
import java.util.List;

public class AssociationImpl implements IAssociationService {

    @Resource
    SignUpDao signUpDao;

    @Override
    public List<RegistrationFormAddition> associationFind() {
        return signUpDao.associationFind();
    }
}
