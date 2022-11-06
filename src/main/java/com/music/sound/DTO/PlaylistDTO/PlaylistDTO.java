package com.music.sound.DTO.PlaylistDTO;

import org.springframework.web.multipart.MultipartFile;

import com.music.sound.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistDTO {
    public PlaylistDTO() {

    }

    public Playlist convertDTOToEntity() {
        Playlist playlist = new Playlist();
        return playlist;
    }
}
