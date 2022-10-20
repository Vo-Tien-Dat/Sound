package com.music.sound.DTO.AlbumDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTORead {
    private String nameAlbum;
    private String nameUser;
    private String pathImage;
    private String pathUrl;
}
