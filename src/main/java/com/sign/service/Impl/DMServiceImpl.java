package com.sign.service.Impl;

import com.sign.dao.DMDao;
import com.sign.entity.*;
import com.sign.service.IDMService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DMServiceImpl implements IDMService {

    @Resource
    DMDao dmDao;

    @Override
    public List<MZDM> findMZDM() {
        return dmDao.findMZDM();
    }

    @Override
    public List<ZZMMDM> findZZMMDM() {
        return dmDao.findZZMMDM();
    }

    @Override
    public List<BYXXDM> findBYXXDM() {
        return dmDao.findBYXXDM();
    }

    @Override
    public List<HJDM> findHJDM() {
        return dmDao.findHJDM();
    }

    @Override
    public List<HJDM> likeHJDM(String hjdmmc) {
        return dmDao.likeHJDM(hjdmmc);
    }

    @Override
    public List<BKZY> findBKZY() {
        return dmDao.findBKZY();
    }

//    @Override
//    public void fileLoad(String fname) {
//        dmDao.fileLoad(fname);;
//    }


}
