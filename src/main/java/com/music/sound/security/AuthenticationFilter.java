package com.music.sound.security;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.music.sound.Exception.NotMatchPassword;
import com.music.sound.utils.AppConstants;

import javax.servlet.http.HttpServletResponse;
@Component
@Order(100)
public class AuthenticationFilter implements Filter{

    // @Autowired
    // private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
       
        String token = getJwtFromRequest(httpServletRequest);
        if(tokenNotNull(token))
        { 
        //    logger.info(jwtTokenUtil.getUsernameFromToken(token));
        }
        else {
            try{
                String username = httpServletRequest.getParameter("username");
                String password = httpServletRequest.getParameter("password");
                Authentication usernamepasswordAuthenticationToken = new UsernamepasswordAuthenticationToken(username, password);
                // Authentication authenticationManager = customAuthenticationManager.authenticate(usernamepasswordAuthenticationToken);
                // String tokenNew = jwtTokenUtil.generateToken(authenticationManager);
                // httpServletResponse.setHeader(AppConstants.DEFAULT_PARAMETER_TOKEN, tokenNew);
            }
            catch(UsernameNotFoundException ex){
               logger.info(ex.getMessage());
            }     
            catch(NotMatchPassword ex){
                logger.info(ex.getMessage());
            }
            catch(Exception ex){
                logger.info(ex.getMessage());
            }
        }

        
        filterChain.doFilter(servletRequest, servletResponse);   
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getParameter(AppConstants.DEFAULT_PARAMETER_TOKEN);
        logger.info(token);
        return token;
    }

    public Boolean tokenNotNull(String token){
        return token != null;
    }
}
