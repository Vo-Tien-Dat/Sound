package com.music.sound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.config.Constant;
import com.music.sound.model.Playlist;

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
}
