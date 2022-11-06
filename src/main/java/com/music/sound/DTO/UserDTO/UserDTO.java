package com.music.sound.DTO.UserDTO;

import org.springframework.stereotype.Component;

import com.music.sound.model.User;

@Component
public class UserDTO {
    private String username;
    private String password;
    private String email;

    public UserDTO() {

    }

    public User convertDTOToEntity(UserLoginDTO userLoginDTO) {
        User user = new User();

        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    public User convertDTOToEntity(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String email = userRegisterDTO.getEmail();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return user;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
