package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

import com.music.sound.DTO.PlaylistDTO.PlaylistDTOInsert;
import com.music.sound.DTO.PlaylistDTO.PlaylistDTORead;
import com.music.sound.model.Playlist;
import com.music.sound.service.PlaylistService;

import java.util.List;

@Controller
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping(value = "/playlist/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/playlist";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    public ModelAndView getPlaylist() {
        String pathFile = "/page/playlist/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("playlistDTOInsert", new PlaylistDTOInsert());

        List<PlaylistDTORead> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));

        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.POST)
    public ModelAndView postPlaylist(@ModelAttribute("playlistDTOInsert") PlaylistDTOInsert playlistDTOInsert) {

        String namePlaylist = playlistDTOInsert.getNamePlaylist();

        Playlist playlist = new Playlist();

        playlist.setNamePlaylist(namePlaylist);

        String urlRedirect = playlistService.getUrlToRedirect(playlist);

        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/playlist/{id}", method = RequestMethod.GET)
    public ModelAndView getPlaylistId() {
        String pathFile = "/page/playlist_id/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }
}
