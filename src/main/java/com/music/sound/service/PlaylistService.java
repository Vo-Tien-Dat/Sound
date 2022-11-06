package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DTO.PlaylistDTO.PlaylistDTORead;
import com.music.sound.DTO.PlaylistDTO.PlaylistDTOReadByIdPlaylist;
import com.music.sound.config.Constant;
import com.music.sound.model.Playlist;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import com.music.sound.model.User;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistDAO playlistDAO;

    private String getIdPlaylist(Playlist playlist) {
        String id = playlistDAO.getIdPlaylistBeforeInsert(playlist);
        return id;
    }

    public String getUrlToRedirect(Playlist playlist) {

        String redirect = Constant.REDIRECT;
        String urlStaticPlaylist = Constant.URL_STATIC_ADD_PLAYLIST;
        String idPlaylist = getIdPlaylist(playlist);

        String url = redirect + urlStaticPlaylist + idPlaylist;

        return url;
    }

    // để lấy tất cả các playlist trong 1 user
    public List<PlaylistDTORead> getAllPlaylistByIdUser(String idUser) {
        List<Playlist> records = playlistDAO.findPlaylistByIdUser(idUser);

        List<PlaylistDTORead> playlistDTOReads = new ArrayList<>();

        for (Playlist record : records) {
            PlaylistDTORead playlistDTORead = new PlaylistDTORead();

            UUID uuid = record.getId();
            String id = uuid.toString();
            String namePlaylist = record.getNamePlaylist();
            String pathImage = record.getPathImage();
            User user = record.getUser();
            String nameUser = user.getNameUser();

            playlistDTORead.setPathUrl(id);
            playlistDTORead.setPathImage(pathImage);
            playlistDTORead.setNamePlaylist(namePlaylist);
            playlistDTORead.setNameUser(nameUser);

            playlistDTOReads.add(playlistDTORead);
        }

        return playlistDTOReads;
    }

    public PlaylistDTOReadByIdPlaylist getPlaylistByIdPLaylist(String idPlaylist) {
        PlaylistDTOReadByIdPlaylist record = new PlaylistDTOReadByIdPlaylist();

        try {
            Playlist playlist = playlistDAO.findPlaylistByIdPlaylist(idPlaylist);

            String namePlaylist = playlist.getNamePlaylist();
            String pathImage = playlist.getPathImage();
            User user = playlist.getUser();
            String nameUser = user != null ? user.getNameUser() : "user is null";

            record.setIdPlaylist(idPlaylist);
            record.setNamePlaylist(namePlaylist);
            record.setPathImage(pathImage);
            record.setNameUser(nameUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return record;
    }

}
