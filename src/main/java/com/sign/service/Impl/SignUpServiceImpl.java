package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.utils.FunctionApplication;
import com.sign.service.ISignUpService;
import com.sign.vo.RegistrationFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SignUpServiceImpl implements ISignUpService {
    @Autowired
    private SignUpDao signUpDao;

    @Override
    public boolean insertStudent(RegistrationFormVo collect) {
        boolean flag = false;
        synchronized (SignUpServiceImpl.class) {
            //检测名字中的空格
            collect.setSname(collect.getSname().replace(" ", ""));
            if (!FunctionApplication.toStringGid(collect.getGid()) || !FunctionApplication.posLength(collect.getPos()) ||
                    !FunctionApplication.posLength(collect.getNid()) || !FunctionApplication.posLength(collect.getParent()) ||
                    !FunctionApplication.posLength(collect.getPerson())) {
                return false;
            }
            flag = signUpDao.insertStudent(collect);
        }
        return flag;

    }

    @Override
    public boolean insertSecStudent(RegistrationFormVo collectVo) {
        return signUpDao.insertSecStudent(collectVo);
    }

    @Override
    public List<RegistrationForm> findStudent() {
        return signUpDao.findStudent();
    }

    @Override
    public RegistrationForm selectStudentById(String dId) {
        return signUpDao.selectStudentById(dId);
    }

    @Override
    public Integer updateStudent(RegistrationFormVo collect) {
        collect.setSname(collect.getSname().replace(" ", ""));
        if (!FunctionApplication.toStringGid(collect.getGid()) || !FunctionApplication.posLength(collect.getPos()) ||
                !FunctionApplication.posLength(collect.getNid()) || !FunctionApplication.posLength(collect.getParent()) ||
                !FunctionApplication.posLength(collect.getPerson())) {
            return 0;
        }
        return signUpDao.updateStudent(collect);
    }

    @Override
    public Integer updateSecStudent(RegistrationFormVo collect) {
        return signUpDao.updateSecStudent(collect);
    }

    @Override
    public List<RegistrationForm> findStudentdId() {
        return signUpDao.findStudentdId();
    }

    @Override
    public List<RegistrationFormAddition> associationFind() {
        return signUpDao.associationFind();
    }

    @Override
    public RegistrationFormAddition associationSecFind(String did) {
        return signUpDao.associationSecFind(did);
    }


}
