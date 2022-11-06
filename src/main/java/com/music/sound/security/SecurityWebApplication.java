package com.music.sound.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityWebApplication {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    // @Autowired
    // private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("oke");
        // httpSecurity
        // .cors()
        // .and()
        // .csrf()
        // .disable()
        // .authorizeHttpRequests()
        // .antMatchers("/login", "/home", "/css/**", "/register")
        // .permitAll();

        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests().anyRequest().permitAll();
        System.out.println("oke");
        return httpSecurity.build();
    }
}
