package com.music.sound.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import com.music.sound.security.providers.UsernamepasswordProvider;
import org.springframework.security.core.AuthenticationException;

public class UsernamepasswordManager implements AuthenticationManager{
    @Autowired
    private UsernamepasswordProvider usernamepasswordProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        return usernamepasswordProvider.authenticate(authentication); 
    }
}
