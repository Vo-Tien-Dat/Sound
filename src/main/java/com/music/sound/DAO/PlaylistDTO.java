package com.music.sound.DAO;

import lombok.Data;

@Data
public class PlaylistDTO {
    private String idPlaylist;
    private String namePlaylist;
    private Long numberSound;
    private Long numberViewer;
    private String pathImage;
    private String nameUser;
}
