package com.music.sound.security.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.lang.Object;
import java.lang.Exception;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws java.lang.Exception {
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, java.lang.Object handler,
            ModelAndView modelAndView) throws java.lang.Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, java.lang.Object handler,
            Exception ex)
            throws java.lang.Exception {
    }

}