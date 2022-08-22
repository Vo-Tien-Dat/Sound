package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.music.sound.repository.UserRepository;
import com.music.sound.model.User;
import com.music.sound.model.UserDetailsOverwrite;
public class UserDetailsServiceOverwrite implements UserDetailsService{
    
    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetailsOverwrite loadUserByUsername(String username) throws UsernameNotFoundException{
        
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserDetailsOverwrite(user);
    }
}
