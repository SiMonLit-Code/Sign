package com.sign.service.Impl;

import com.sign.dao.ConfirmDao;
import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.ConfirmInfo;
import com.sign.service.IConfirmInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfirmInfoImpl implements IConfirmInfoService {
    @Resource
    ConfirmDao confirmDao;
    @Resource
    SignUpDao signUpDao;

    @Override
    public ConfirmInfo findConfirmInfoById(String id) {
        return confirmDao.findConfirmInfoById(id);
    }

    @Override
    public void ConfirmInfoBe(ConfirmInfo confirmInfo) {
        Add add=signUpDao.associationSecFind(confirmInfo.getSFZH());
//        if (confirmInfo.getSFZH().equals(add.getDid() && confirmInfo)){
//
//        }
    }

    @Override
    public Integer updateConfirmInfoById(String BKZY, String CINFO) {
        return confirmDao.updateConfirmInfoById(BKZY,CINFO);
    }


}
