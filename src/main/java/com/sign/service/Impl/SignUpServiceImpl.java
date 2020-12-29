package com.sign.service.Impl;

import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.function.FunctionApplication;
import com.sign.service.ISignUpService;
import com.sign.vo.CollectVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service

public class SignUpServiceImpl implements ISignUpService {
    @Resource
    SignUpDao signUpDao;

    @Resource
    FunctionApplication functionApplication;


    @Override
    public boolean insertStudent(CollectVo collect) {
        boolean flag =false ;
        synchronized (this) {
            //检测名字中的空格
            collect.setSname(collect.getSname().replace(" ",""));
//            System.out.println(collect.getGid());
                if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                        !functionApplication.posLength(collect.getPerson())){
                    return false;
                }
                flag =  signUpDao.insertStudent(collect);
            }
//        return signUpDao.insertStudent(collect);
//        return true;
        return flag ;

    }

    @Override
    public boolean insertSecStudent(CollectVo collectVo) {
        return signUpDao.insertSecStudent(collectVo);
    }

    @Override
    public List<Collect> findStudent() {
        return signUpDao.findStudent();
    }

    @Override
    public Collect selectStudentById(String dId) {
        return signUpDao.selectStudentById(dId);
    }

    @Override
    public Integer updateStudent(CollectVo collect) {
        collect.setSname(collect.getSname().replace(" ",""));
        if (!functionApplication.toStringGid(collect.getGid()) || !functionApplication.posLength(collect.getPos().toString()) ||
                !functionApplication.posLength(collect.getNid().toString()) || !functionApplication.posLength(collect.getParent())  ||
                !functionApplication.posLength(collect.getPerson())){
            return 0;
        }
        return signUpDao.updateStudent(collect);
    }

    @Override
    public Integer updateSecStudent(CollectVo collect) {
        return signUpDao.updateSecStudent(collect);
    }

    @Override
    public List<Collect> findStudentdId() {
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
