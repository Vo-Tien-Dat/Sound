package com.music.sound.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Configuration
public class SecurityWebApplication implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(SecurityWebApplication.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        logger.info("run security filter chain");
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .antMatchers("/login")
            .permitAll();
        return http.build();
    }
}
