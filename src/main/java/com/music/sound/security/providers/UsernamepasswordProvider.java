package com.music.sound.security.providers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.music.sound.security.auth.UsernamepasswordAuthenticationToken;
import com.music.sound.service.UserDetailsServiceOverwrite;
import org.springframework.security.core.AuthenticationException;
import com.music.sound.model.UserDetailsOverwrite;

public class UsernamepasswordProvider implements AuthenticationProvider{
   
    @Autowired
    private UserDetailsServiceOverwrite userDetailsServiceOverwrite;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        String username = authentication.getName();
        UserDetailsOverwrite user = userDetailsServiceOverwrite.loadUserByUsername(username);
        
        return new UsernamepasswordAuthenticationToken(user, null);
        
    }

    @Override
    public boolean supports(Class<?> aClass){
        return UsernamepasswordAuthenticationToken.class.equals(aClass);
    }
    
}
