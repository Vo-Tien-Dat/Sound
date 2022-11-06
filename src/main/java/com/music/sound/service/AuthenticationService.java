package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.sound.DAO.UserDAO;
import com.music.sound.DTO.UserDTO.UserDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.model.User;

@Service
public class AuthenticationService {

    private String message;

    private String urlRedirect;

    @Autowired
    private UserDTO userDTO;

    @Autowired
    private UserDAO userDAO;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setUrlRedirect(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    public String getUrlRedirect() {
        return this.urlRedirect;
    }

    public Boolean createNewUser(UserRegisterDTO userRegisterDTO) {

        Boolean result = false;

        try {
            User user = userDTO.convertDTOToEntity(userRegisterDTO);
            userDAO.insertUser(user);
            result = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public Boolean readUser() {
        return false;
    }

    public Boolean updateUser() {
        return false;
    }

    public Boolean deleteUser() {
        return false;
    }

}
