package com.sign.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-11 18:06:00
 * @description :
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //处理登录成功逻辑
//        response.setHeader("Content-Type", "application/json;charset=utf-8");
//        request.setAttribute("Content-Type","application/json;charset=utf-8");

        request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
    }
}
