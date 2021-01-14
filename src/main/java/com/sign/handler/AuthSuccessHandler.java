package com.sign.handler;

import com.sign.jwt.JwtTokenUtils;
import com.sign.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-11 18:06:00
 * @description :
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    IRedisService iRedisService;

    @Value("${admin.usernames}")
    String[] adminUsername;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //处理登录成功逻辑
        //密码正确，生成token，设置cookie
        String username = request.getParameter("username");
        String token = JwtTokenUtils.generateToken(username);
        Cookie cookie = new Cookie("access_token", token);
        response.addCookie(cookie);
        //存入redis
        iRedisService.saveToken(username,token);

        //判断角色
        if (Arrays.asList(adminUsername).contains(username)){
            request.setAttribute("flag","admin");
        }else {
            request.setAttribute("flag","user");
        }
        request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
    }
}
