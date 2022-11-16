package com.music.sound.DAO;

import lombok.Data;

@Data
public class AlbumDTO {
    private String idAlbum;
    private String nameAlbum;
    private String nameSinger;
    private Long numberSound;
    private Long numberViewer;
    private String pathImage;
}
