package com.music.sound.DAO;

import lombok.Data;

@Data
public class AlbumDTO {
    private String idAlbum;
    private String nameAlbum;
    private Long numberSound;
    private Long numberViewer;
    private String pathImage;
    private String nameUser;
}
