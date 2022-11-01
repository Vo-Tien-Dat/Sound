package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DTO.AlbumDTO.AlbumDTORead;
import com.music.sound.DTO.AlbumDTO.ConvertAlbum;
import com.music.sound.model.Album;

@Service
public class AlbumService {

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private ConvertAlbum convertAlbum;

    public List<AlbumDTORead> getAllAlbum() {
        List<AlbumDTORead> albumDTOReads = new ArrayList<>();

        List<Album> albums = albumDAO.findAllAlbum();
        for (Album album : albums) {
            AlbumDTORead albumDTORead = convertAlbum.convertEntityToDTO(album);
            albumDTOReads.add(albumDTORead);
        }

        return albumDTOReads;
    }

}
