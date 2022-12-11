package com.music.sound.DTO;

import lombok.Data;

@Data
public class PasswordDTO {
    private String currentPassword;
    private String newPassword;

    public PasswordDTO() {

    }
}
