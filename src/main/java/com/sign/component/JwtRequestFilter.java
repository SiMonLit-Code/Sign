package com.sign.component;

import com.sign.entity.User;
import com.sign.jwt.JwtTokenUtils;
import com.sign.service.IRedisService;
import com.sign.service.IRegisterService;
import com.sign.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 16:10:00
 * @description :JwtRequestFilter，过滤JWT请求，验证"Bearer token"格式，校验Token是否正确
 */
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private MyUserDetailsService detailsService;

    private IRedisService iRedisService;

    private IRegisterService iRegisterService;

    public JwtRequestFilter(MyUserDetailsService detailsService, IRedisService iRedisService, IRegisterService iRegisterService) {
        this.detailsService = detailsService;
        this.iRedisService = iRedisService;
        this.iRegisterService = iRegisterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain filterChain) throws ServletException, IOException {

        //从cookie中取出token
        String access_token = null;
        if (null != req.getCookies()) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("access_token")) {
                    access_token = cookie.getValue();
                }
            }
        }
        //通过放行
        if (null != access_token) {
            String username = JwtTokenUtils.getNameFromToken(access_token);
            if (iRedisService.validateAccessToken(username, access_token)) {
                filterChain.doFilter(req, resp);
                return;
            }else {
                SecurityContextHolder.clearContext();
            }
        }
        String name = req.getParameter("username");
        //校验username
        if (null != name && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = detailsService.loadUserByUsername(name);
            if (null != userDetails) {
                //检验密码
                String password = req.getParameter("password");
                if (iRegisterService.registerFind(new User(name, password))) {
                    //授权
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                    //将授权成功的用户保存到SecurityContextHolder，交给SpringSecurity，可以全局获取到
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } else {
            log.warn("username is null or AuthenticationObject is null");
        }
        filterChain.doFilter(req, resp);
    }
}
