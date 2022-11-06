package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DTO.UserDTO.ConvertUser;
import com.music.sound.DTO.UserDTO.UserDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.model.User;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ConvertUser convertUserDTO;

    public UserDTO runConvertEntityToDTO() {
        String username = "votiendatf";
        User user = userDAO.findUserByUsername(username);
        UserDTO userDTO = convertUserDTO.convertEntityToDTO(user);
        return userDTO;
    }

    public void create(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String email = userRegisterDTO.getEmail();
        if (username == null || username.length() == 0) {
            throw new NullPointerException("username is empty");
        }

        if (password == null || password.length() == 0) {
            throw new NullPointerException("password is empty");
        }

        if (email == null || email.length() == 0) {
            throw new NullPointerException("email is empty");
        }

        User user = convertUserDTO.convertDTOToEntity(userRegisterDTO);
        try {
            userDAO.insertUser(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
