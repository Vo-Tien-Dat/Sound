package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.music.sound.repository.UserRepository;
import com.music.sound.model.User;
import com.music.sound.model.CustomUserDetails;
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    public UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserDetails(user);
    }
}
