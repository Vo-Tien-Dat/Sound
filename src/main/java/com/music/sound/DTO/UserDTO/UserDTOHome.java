package com.music.sound.DTO.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class UserDTOHome {
    private String nameUser;
    private String pathImage;
    private String numberSounds;
    private String numberAlbums;
    private String numberPlaylists;
}
