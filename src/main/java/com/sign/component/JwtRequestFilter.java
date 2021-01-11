package com.sign.component;

import com.sign.jwt.JwtTokenUtils;
import com.sign.service.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 16:10:00
 * @description :JwtRequestFilter，过滤JWT请求，验证"Bearer token"格式，校验Token是否正确
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = req.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        //JWT报文头是"Bearer token",去除Bearer
        if (null != header && header.startsWith("Bearer")){
            jwtToken = header.substring(7);
            try{
                username = JwtTokenUtils.getNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        //校验username
        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = detailsService.loadUserByUsername(username);
            if (JwtTokenUtils.validateToken(jwtToken,userDetails)){
                //授权用户
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                //将授权成功的用户保存到SecurityContextHolder，交给SpringSecurity，可以全局获取到
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }else {
            log.warn("username is null or AuthenticationObject is null");
        }
        filterChain.doFilter(req,resp);
    }
}
