package com.sign.service;

import com.sign.entity.ConfirmInfo;

public interface IConfirmInfoService {
    //查询单个
    ConfirmInfo findConfirmInfoById(String id);

    //信息确认
    public void ConfirmInfoBe(ConfirmInfo confirmInfo);

    //修改专业
    Integer updateConfirmInfoById(String BKZY,String CINFO);
}
