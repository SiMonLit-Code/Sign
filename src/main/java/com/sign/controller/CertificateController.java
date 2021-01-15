package com.sign.controller;

import com.sign.constant.ExamInformation;
import com.sign.entity.RegistrationFormAddition;
import com.sign.service.ISignUpService;
import com.sign.service.SpringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 准考证打印表控制层
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
@Controller
@RequestMapping("/registration")
public class CertificateController {
    /**
     * 服务对象
     */
    @Resource
    private SpringService springService;

    @Resource
    private ISignUpService iSignUpService;

    /**
     * 通过主键查询单条数据
     *
     *
     * @return 单条数据
     */
    @GetMapping("/certificate")
    public String certificate(Model model) {
        String username = ExamInformation.userDetails.getUsername();
        RegistrationFormAddition addstudent = iSignUpService.associationSecFind(username);
        if (addstudent == null) {
//            System.out.println("请先报名");
            return "emp/updatefalse";
        } else {
            String pName = username + ".jpg";
            model.addAttribute("zp", "/imagesSFZ/" + pName);
            model.addAttribute("stu", springService.queryById(username));
            return "emp/certification";
        }
    }

}