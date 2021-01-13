package com.sign.controller;

import com.sign.entity.RegistrationFormAddition;
import com.sign.service.ISignUpService;
import com.sign.service.SpringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (Spring)表控制层
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
@Controller
public class SpringController {
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
    @GetMapping("/AdTicket")
    public String selectOne(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String did = (String) session.getAttribute("id");
        RegistrationFormAddition addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
        if (addstudent == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        } else {
            String pName = did + ".jpg";
            model.addAttribute("zp", "/imagesSFZ/" + pName);
            model.addAttribute("stu", springService.queryById(did));
            return "AdTicket";
        }
    }

}