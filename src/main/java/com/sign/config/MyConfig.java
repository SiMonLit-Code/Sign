package com.sign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagesSFZ/**").addResourceLocations("file:E://apache-tomcat-8.5.45/webapps/imagesSFZ/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**上传地址*/
//    @Value("${file.upload.path}")
//    private String filePath;
//    /**显示相对地址*/
//    @Value("${file.upload.path.relative}")
//    private String fileRelativePath;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main").setViewName("dashboard");
        registry.addViewController("/mainAd").setViewName("adminboard");
        // 管理员账户修改页面
        registry.addViewController("/enter").setViewName("admin/adminUpdate");
    }


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(fileRelativePath).
//                addResourceLocations("file:/" + filePath);
//    }

    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/returnAd","/adminUpdate","/adminUpdatePage","/payment/orderSubmit","/index.html","/","/loginStu","/enter","/loginAdm","/register","/login","/asserts/**");
//    }

    //写该方法目的是让自动配置失效，使用自己的配置
//    @Bean
//    public LocaleResolver localeResolver(){
//        return new MyLocaleResolver();
//    }

}
