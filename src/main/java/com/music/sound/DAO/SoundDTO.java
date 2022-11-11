package com.music.sound.DAO;

import lombok.Data;

@Data
public class SoundDTO {
    private String idSound;
    private String nameSound;
    private Long numberViewer;
    private String nameSinger;
    private String pathAudio;
    private String pathImage;
    private String nameTypeSound;
    private String nameUser;
    private String nameAlbum;
    private String namePlaylist;
}
