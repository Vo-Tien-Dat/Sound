package com.music.sound.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.music.sound.security.manager.UsernamepasswordManager;
import com.music.sound.service.UserDetailsServiceOverwrite;
import com.music.sound.security.providers.UsernamepasswordProvider;
@Configuration
public class ConfigurationBean{

    @Bean
    public UsernamepasswordManager getUsernamepasswordManager(){
        UsernamepasswordManager usernamepasswordManager = new UsernamepasswordManager();
        return usernamepasswordManager;
    }

    @Bean
    public UserDetailsServiceOverwrite getUserDetailsServiceOverview(){
        UserDetailsServiceOverwrite user = new UserDetailsServiceOverwrite();
        return user;
    }

    @Bean
    public UsernamepasswordProvider getUsernamepasswordProvider(){
        UsernamepasswordProvider usernamepasswordManager = new UsernamepasswordProvider();
        return usernamepasswordManager;
    }
}
