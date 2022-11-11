package com.music.sound.DAO;

import lombok.Data;

@Data
public class UserDTO {
    private String idUser;
    private String userName;
    private String password;
    private String email;
    private String pathImage;
    private String description;
    private String nameUser;
}
