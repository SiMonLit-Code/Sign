package com.sign.controller;

import com.sign.constant.ExamInformation;
import com.sign.entity.User;
import com.sign.service.IRegisterService;
import com.sign.utils.LoginAndRegisterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
public class LoginAndRegisterController {
    @Autowired
    IRegisterService iRegisterService;

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @GetMapping("/exit")
    public String exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginMsg");
        session.removeAttribute("loginAdmMsg");
        return "/index";
    }


    /**
     * "Content-Type":application/x-www-form-urlencoded;charset=UTF-8 不需要加@RequestBody注解
     *
     * @return
     */
    //学生登陆
    @PostMapping(value = "/loginStu")
    public ModelAndView loginStudent(HttpServletRequest request) {
        ExamInformation.userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String flag = (String) request.getAttribute("flag");
        return LoginAndRegisterUtil.loginStatusInf(flag);
    }


    /**
     * 注册页面预处理
     */
    @PostMapping("/register")
    public ModelAndView registerInf(User user, @RequestParam("repetitionPsw") String repetitionPsw) {
        Integer count = iRegisterService.registerFindAc(user);
        ModelAndView mv = LoginAndRegisterUtil.registerPswJudge(user, repetitionPsw, count);
        if ("index".equals(mv.getViewName())) {
            iRegisterService.registerInsert(user);
        }
        return mv;
    }
}


