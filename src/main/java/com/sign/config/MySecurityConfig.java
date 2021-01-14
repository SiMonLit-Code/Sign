package com.sign.config;

import com.sign.component.JwtRequestFilter;
import com.sign.handler.AuthFailureHandler;
import com.sign.handler.AuthLogoutHandler;
import com.sign.handler.AuthSuccessHandler;
import com.sign.service.IRedisService;
import com.sign.service.IRegisterService;
import com.sign.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-12-29 15:45:00
 * @description :
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService detailsService;

    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private AuthLogoutHandler authLogoutHandler;

    @Autowired
    private IRedisService iRedisService;

    @Autowired
    private IRegisterService iRegisterService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                //指定登录页的路径
                .loginPage("/login")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/loginStu")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .logoutUrl("/exit")
                .addLogoutHandler(authLogoutHandler)
                .permitAll()
                .and()
                .authorizeRequests()
                //学生权限
                .antMatchers("/registration/**").hasRole("USER")
                //管理员权限
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login","/index.html","/exit","/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtRequestFilter(detailsService,iRedisService,iRegisterService), UsernamePasswordAuthenticationFilter.class);

        //关闭跨域
        http .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/asserts/**");
//        web.ignoring().antMatchers(HttpMethod.GET,"/register");
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
