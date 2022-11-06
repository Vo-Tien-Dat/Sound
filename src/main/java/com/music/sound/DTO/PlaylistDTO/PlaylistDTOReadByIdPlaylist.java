package com.music.sound.DTO.PlaylistDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlaylistDTOReadByIdPlaylist {
    private String idPlaylist;

    private String namePlaylist;

    private String pathImage;

    private String nameUser;

}
