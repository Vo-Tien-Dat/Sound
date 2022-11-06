package com.music.sound.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.music.sound.service.CustomUserDetailsService;
import com.music.sound.security.AuthenticationFilter;
// import com.music.sound.security.CustomAuthenticationManager;
// import com.music.sound.security.CustomAuthenticationProvider;
// import com.music.sound.security.JwtAuthenticationEntryPoint;
import com.music.sound.security.JwtTokenUtil;
@Configuration
public class ConfigurationBean{

    // @Bean
    // public CustomAuthenticationManager getUsernamepasswordManager(){
    //     CustomAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager();
    //     return customAuthenticationManager;
    // }

    @Bean
    public CustomUserDetailsService getUserDetailsServiceOverview(){
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        return customUserDetailsService;
    }

    // @Bean
    // public CustomAuthenticationProvider getUsernamepasswordProvider(){
    //     CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
    //     return customAuthenticationProvider;
    // }

    @Bean
    public AuthenticationFilter getAuthenticationFilter(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        return authenticationFilter;
    }


    // @Bean 
    // public JwtAuthenticationEntryPoint getJwtAuthenticationEntryPoint(){
    //     JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
    //     return jwtAuthenticationEntryPoint;
    // }


    @Bean
    public JwtTokenUtil getTokenUtil(){
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        return jwtTokenUtil;
    }
}
