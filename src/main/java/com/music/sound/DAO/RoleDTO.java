package com.music.sound.DAO;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleDTO implements Serializable {
    private String idUser;
    private String roleUser;
}
