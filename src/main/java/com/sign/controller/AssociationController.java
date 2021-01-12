package com.sign.controller;

import com.alibaba.excel.EasyExcel;
import com.sign.dao.SignUpDao;

import javax.annotation.Resource;

//@Controller
public class AssociationController {
    @Resource
    SignUpDao signUpDao;

//    @GetMapping("/AdTicket")
    public String association(){

        EasyExcel.read("D:\\sign\\123.xlsx");
        return "AdTicket";
    }
}
