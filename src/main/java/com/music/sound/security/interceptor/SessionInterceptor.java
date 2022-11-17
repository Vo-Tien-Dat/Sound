package com.music.sound.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.lang.Object;
import java.lang.Exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

        private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                        throws Exception {
                logger.info("prehandle interceptor of session");
                HttpSession session = request.getSession();
                String idSession = session.getId();
                String url = request.getRequestURI();
                System.out.println(url);
                if (session.getAttribute(idSession) == null) {
                        response.sendRedirect("/login");
                        return false;
                }
                return true;
        }

        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                        ModelAndView modelAndView) throws java.lang.Exception {

                logger.info("posthandle interceptor of session");
        }

        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                        Exception ex)
                        throws java.lang.Exception {
        }

}
