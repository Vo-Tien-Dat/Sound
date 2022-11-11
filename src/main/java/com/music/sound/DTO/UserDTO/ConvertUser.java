package com.music.sound.DTO.UserDTO;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.music.sound.model.User;

@Component
public class ConvertUser {

    public User convertDTOToEntity(UserDTO userDTO) throws NullPointerException {

        User user = new User();
        String message = "Output Object User Null";
        if (userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getEmail() == null) {
            throw new NullPointerException(message);
        }
        user.setUserName(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public User convertDTOToEntity(UserRegisterDTO userRegisterDTO) throws NullPointerException {

        User user = new User();
        user.setUserName(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setEmail(userRegisterDTO.getEmail());
        return user;
    }

    public UserDTO convertEntityToDTO(User user) throws UsernameNotFoundException {
        UserDTO userDTO = new UserDTO();
        String message = "Not Found User";
        if (user.getUserName() == null) {
            throw new UsernameNotFoundException(message);
        }
        userDTO.setUsername(user.getUserName());
        userDTO.setUsername(user.getPassword());
        return userDTO;
    }
}
