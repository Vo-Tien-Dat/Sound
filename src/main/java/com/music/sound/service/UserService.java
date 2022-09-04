package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.sound.DTO.UserDTO.ConvertUserDTO;
import com.music.sound.DTO.UserDTO.UserDTO;
import com.music.sound.model.User;
import com.music.sound.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private ConvertUserDTO convertUserDTO;

    public UserDTO runConvertEntityToDTO(){
        String username = "votiendatf";
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = convertUserDTO.convertEntityToDTO(user);
        return userDTO;
    }
}
