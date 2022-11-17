package com.music.sound.security;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.servlet.ServletException;
import com.music.sound.utils.AppConstants;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Order(100)
public class AuthenticationFilter implements Filter {

    // @Autowired
    // private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Cookie cookie[] = httpServletRequest.getCookies();
        // System.out.println(cookie[0].getValue());
        // httpServletResponse.addHeader("access_token", "Add access token in spring
        // mvc");
        // httpServletResponse.addCookie(new Cookie("token",
        // "ddddddddddddddddddddddddddddddddddddddddddddddddddddd"));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter(AppConstants.DEFAULT_PARAMETER_TOKEN);
        logger.info(token);
        return token;
    }

    public Boolean tokenNotNull(String token) {
        return token != null;
    }
}
