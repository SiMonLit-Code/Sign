package com.sign.utils;

import com.sign.constant.ExamInformation;
import com.sign.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-13 15:39:00
 * @description :
 */
public class LoginAndRegisterUtil {
    /**
     * 判断密码格式
     *
     * @param user
     * @param repetitionPsw
     * @return
     */
    public static ModelAndView registerPswJudge(User user, String repetitionPsw, Integer count) {
        ModelAndView mv = new ModelAndView();
        //        System.out.println("----------注册请求：----------");
        if (!repetitionPsw.equals(user.getPassword())) {
            mv.addObject("pwdmsgnum", "密码输入不一致");
            mv.setViewName("register");
        }
        if (user.getPassword().length() < 6) {
            mv.addObject("pwdmsglen", "密码长度6-12位");
            mv.setViewName("register");
        }
        if (user.getUsername().length() != 18) {
            mv.addObject("acountmsgnum", "身份证位数有误");
            mv.setViewName("register");
        }
        if (count == 0) {
            mv.setViewName("index");
        } else {
            mv.addObject("acountmsgcf", "身份证已被注册，有问题请联系管理员");
            //      System.out.println("----------注册请求结束：----------");
            mv.setViewName("register");
        }
        return mv;
    }

    /**
     * 登陆
     *
     * @param status
     * @return
     */
    public static ModelAndView loginStatusInf(String status) {
        ModelAndView mv = new ModelAndView();
        if ("user".equals(status)) {
            //正确登录
            ExamInformation.userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            mv.setViewName("redirect:/main");
        }else if ("admin".equals(status)) {
            //管理员登录
            mv.setViewName("redirect:/mainAd");
        } else {
            //密码错误返回登录页面
            mv.addObject("msg", "身份证或密码错误");
            mv.setViewName("index");
        }
        return mv;
    }

}
