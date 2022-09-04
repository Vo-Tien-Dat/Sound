package com.music.sound.DTO.UserDTO;

import org.springframework.stereotype.Component;

import com.music.sound.model.User;

@Component
public class ConvertUserDTO {
    public User convertDTOToEntity(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user; 
    }

    public UserDTO convertEntityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setUsername(user.getPassword());
        return userDTO;
    }
}
