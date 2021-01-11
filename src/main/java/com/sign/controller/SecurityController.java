package com.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-11 17:08:00
 * @description :
 */
@Controller
public class SecurityController {
    /**
     * 自定义登录页面
     * @return
     */
    @RequestMapping("/login")
    public String loginDashboard(){
        return "index";
    }

    @RequestMapping("/success")
    public String loginSuccess(){
        return "dashboard";
    }
}
