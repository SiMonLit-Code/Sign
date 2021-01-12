package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.RegistrationForm;
import com.sign.function.FunctionApplication;
import com.sign.service.ISignUpService;
import com.sign.vo.RegistrationFormVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class SignUpServiceImpl implements ISignUpService {
    @Resource
    SignUpDao signUpDao;

    @Resource
    FunctionApplication functionApplication;


    @Override
    public boolean insertStudent(RegistrationFormVo collect) {
        boolean flag =false ;
        synchronized (this) {
            //检测名字中的空格
            collect.setSname(collect.getSname().replace(" ",""));
//            System.out.println(registrationForm.getGid());
                if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                        !functionApplication.posLength(collect.getPerson())){
                    return false;
                }
                flag =  signUpDao.insertStudent(collect);
            }
//        return signUpDao.insertStudent(registrationForm);
//        return true;
        return flag ;

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
        collect.setSname(collect.getSname().replace(" ",""));
        if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                !functionApplication.posLength(collect.getPerson())){
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
    public List<Add> associationFind() {
        return signUpDao.associationFind();
    }

    @Override
    public Add associationSecFind(String did) {
        return signUpDao.associationSecFind(did);
    }


}
