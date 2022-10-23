package com.music.sound.DTO.AlbumDTO;

import org.springframework.stereotype.Component;

import com.music.sound.model.Album;

@Component
public class ConvertAlbum {
    public AlbumDTORead convertEntityToDTO(Album album) {
        AlbumDTORead albumDTORead = new AlbumDTORead();
        String nameAlbum = album.getNameAlbum();
        String nameUser = album.getUser().getUsername();
        String idAlbum = album.getId().toString();

        albumDTORead.setNameAlbum(nameAlbum);
        albumDTORead.setNameUser(nameUser);
        albumDTORead.setPathUrl(idAlbum);

        return albumDTORead;
    }
}
