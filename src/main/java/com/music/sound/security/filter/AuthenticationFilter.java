package com.music.sound.security.filter;
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
import com.music.sound.security.auth.UsernamepasswordAuthenticationToken;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.core.Authentication;
import com.music.sound.security.manager.UsernamepasswordManager;
@Component
@Order(100)
public class AuthenticationFilter implements Filter{

    @Autowired
    private UsernamepasswordManager usernamepasswordManager;

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private List<String> paths =  Arrays.asList("/login", "/home");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;
        // HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        if(matchPath(path)){
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            logger.info("run do filter");
            try{
                Authentication auth = new UsernamepasswordAuthenticationToken(username, password);
                auth = usernamepasswordManager.authenticate(auth);
                chain.doFilter(request, response);
            }
            catch(Exception ex){
                logger.info(ex.getMessage());
            }
            chain.doFilter(request, response);
            
        }
        else{
            logger.info("tack");
        }
        
    }
    public boolean matchPath(String path){
        return this.paths.contains(path);
    }

}
