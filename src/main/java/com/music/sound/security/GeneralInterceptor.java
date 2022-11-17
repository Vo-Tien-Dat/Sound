package com.music.sound.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.lang.Object;
import java.lang.Exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GeneralInterceptor implements HandlerInterceptor {
        private Logger logger = LoggerFactory.getLogger(GeneralInterceptor.class);

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
