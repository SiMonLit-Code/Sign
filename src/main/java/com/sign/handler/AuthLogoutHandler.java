package com.sign.handler;

import com.sign.constant.ExamInformation;
import com.sign.service.IRedisService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-13 12:07:00
 * @description :
 */
@Component
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private IRedisService iRedisService;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        //删除redis中缓存
        iRedisService.deleteToken(user.getUsername());
        ExamInformation.userDetails = null;
        SecurityContextHolder.clearContext();

        response.setHeader("Content-Type", "application/json;charset=utf-8");
        request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
    }
}
