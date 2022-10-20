package com.music.sound.DTO.PlaylistDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTORead {
    private String namePlaylist;
    private String nameUser;
    private String pathImage;
    private String pathUrl;
}
