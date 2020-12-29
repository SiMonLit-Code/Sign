package com.sign.config;

import com.sign.component.JwtRequestFilter;
import com.sign.exception.JwtAuthenticationPointException;
import com.sign.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 15:45:00
 * @description :
 */
@Configurable
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationPointException jwtException;

    @Autowired
    private MyUserDetailsService detailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //设置权限不通过异常
                .exceptionHandling().authenticationEntryPoint(jwtException).and()
                //设置session无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //允许注册和登录的url
                .authorizeRequests().antMatchers("/login","/register").permitAll().and()
                //设置过滤器
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                //页面关闭
                .headers().frameOptions().sameOrigin().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //AuthenticationTokenFilter 将以下的路径忽略
        web.ignoring()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/static/**",
                        "/imagesSFZ/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编码
        auth.userDetailsService(detailsService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return charSequence.toString();
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return s.equals(charSequence.toString());
                    }
                });
    }
}
