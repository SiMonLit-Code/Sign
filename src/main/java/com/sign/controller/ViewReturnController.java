package com.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-11 17:08:00
 * @description :
 */
@Controller
public class ViewReturnController {
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

    @GetMapping("/return")
    public String returnPc() {
        return "redirect:/main";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //跳转修改页面
    @GetMapping("/adminUpdatePage")
    public String adminUpdatePage(){
        return "admin/adminUpdate";
    }

    @GetMapping("/enter")
    public String enterAdm() {
        return "admin/adminlogin";
    }

    @GetMapping("/file")
    public String adFile() {
        return "emp/file";
    }

    @GetMapping("/cx")
    public String xc() {
        return "emp/listcx";
    }

    @GetMapping("/returnAd")
    public String returnAdmin(){
        return "admin/adminlogin";
    }
}
