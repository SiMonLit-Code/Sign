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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationPointException jwtException;

    @Autowired
    private MyUserDetailsService detailsService;

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers().authenticated().and()
                .formLogin().loginPage("/index").loginProcessingUrl("/loginStu").defaultSuccessUrl("/").permitAll()
                // 设置登陆成功页
                .and()
                //设置权限不通过异常
                .exceptionHandling().authenticationEntryPoint(jwtException).and()
                //设置session无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //允许注册和登录的url
                .authorizeRequests().antMatchers("/index","/loginStu").permitAll()
//                .antMatchers("/main").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/dashboard.html").hasAuthority("ROLE_ADMIN").
                .and()

                //设置过滤器
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                //页面关闭
                .headers().frameOptions().sameOrigin().cacheControl();

        http.csrf().disable();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/index.html").permitAll()
                .antMatchers("/dashboard.html").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页的路径
                .loginPage("/login")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/dashboard.html")
                .defaultSuccessUrl("/success")
                .failureForwardUrl("/failure")
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll().and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/asserts/**");
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
