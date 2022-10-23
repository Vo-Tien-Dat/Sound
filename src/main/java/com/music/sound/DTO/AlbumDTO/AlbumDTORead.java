package com.music.sound.DTO.AlbumDTO;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTORead {
    private String nameAlbum;
    
    private String nameUser;

    @Nullable
    private String pathImage;

    private String pathUrl;
}
