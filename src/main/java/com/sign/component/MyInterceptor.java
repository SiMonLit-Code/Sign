package com.sign.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession sessionStu=request.getSession();
        HttpSession sessionAdm=request.getSession();
        String msg=(String)sessionStu.getAttribute("loginMsg");
        String admmsg=(String)sessionAdm.getAttribute("loginAdmMsg");
        if(!StringUtils.isEmpty(msg)&&"success".equals(msg)){
            return true;
        }else {
            if(!StringUtils.isEmpty(admmsg)&&"adminlogin".equals(admmsg)){
                return true;
            }else {
                request.setAttribute("msg","请输入账号和密码");
                request.getRequestDispatcher("/index.html").forward(request,response);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
