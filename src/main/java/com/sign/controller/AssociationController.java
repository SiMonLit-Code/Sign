package com.sign.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.sign.dao.SignUpDao;
import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.service.ISignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
