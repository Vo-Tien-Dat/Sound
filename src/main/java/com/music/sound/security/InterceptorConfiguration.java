package com.music.sound.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.music.sound.security.interceptor.GeneralPurposeInterceptor;
import com.music.sound.security.interceptor.SessionInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private GeneralPurposeInterceptor generalPurposeInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {

        // registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns("/login",
        // "/css/*", "/js/*");

        // registry.addInterceptor(generalPurposeInterceptor).addPathPatterns("/login");

    }

}
