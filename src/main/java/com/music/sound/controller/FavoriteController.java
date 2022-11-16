package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.PlaylistDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import com.music.sound.model.Album;
import java.util.List;
import java.util.ArrayList;

@Controller
public class FavoriteController {

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private PlaylistDAO playlistDAO;

    @RequestMapping(value = "/favorite/*", method = RequestMethod.GET)
    public ModelAndView index() {
        String urlRedirect = "redirect:/favorite/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public ModelAndView getFavorite() {
        String fileView = "/page/favorite/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        List<PlaylistDTO> playlists = new ArrayList<>();
        List<AlbumDTO> albums = new ArrayList<>();
        List<SoundDTO> sounds = new ArrayList<>();
        try {
            modelAndView.addObject("albums", albums);
            modelAndView.addObject("playlists", playlists);
            modelAndView.addObject("sounds", sounds);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }

        return modelAndView;
    }

    // @RequestMapping(value = "/favorite/album/*", method = RequestMethod.GET)
    // public ModelAndView indexAlbum() {
    // String urlRedirect = "redirect:/favorite/album";
    // ModelAndView modelAndView = new ModelAndView(urlRedirect);
    // return modelAndView;
    // }

    // @RequestMapping(value = "/favorite/playlist/*", method = RequestMethod.GET)
    // public ModelAndView indexPlaylist() {
    // String urlRedirect = "redirect:/favorite/playlist";
    // ModelAndView modelAndView = new ModelAndView(urlRedirect);
    // return modelAndView;
    // }

    // @RequestMapping(value = "/favorite/sound/*", method = RequestMethod.GET)
    // public ModelAndView indexSound() {
    // String urlRedirect = "redirect:/favorite/sound";
    // ModelAndView modelAndView = new ModelAndView(urlRedirect);
    // return modelAndView;
    // }

    // @RequestMapping(value = "/favorite/album", method = RequestMethod.GET)
    // public ModelAndView getAlbum() {
    // String fileView = "/page/favorite/index";
    // ModelAndView modelAndView = new ModelAndView(fileView);

    // List<Album> albums = new ArrayList<>();

    // try {
    // albums = albumDAO.findAllAlbum();
    // modelAndView.addObject("albums", albums);
    // } catch (Exception ex) {
    // System.out.println(ex.getMessage());
    // }
    // return modelAndView;
    // }

    // @RequestMapping(value = "/favorite/playlist", method = RequestMethod.GET)
    // public ModelAndView getPlaylist() {
    // String fileView = "/page/favorite/index";
    // ModelAndView modelAndView = new ModelAndView(fileView);

    // List<PlaylistDTO> playlists = new ArrayList<>();
    // try {
    // playlists = playlistDAO.readAllPLaylist();
    // modelAndView.addObject("playlists", playlists);
    // } catch (Exception ex) {
    // System.out.println(ex.getMessage());
    // }

    // return modelAndView;
    // }

    // @RequestMapping(value = "/favorite/sound", method = RequestMethod.GET)
    // public ModelAndView getSound() {
    // String fileView = "/page/favorite/index";
    // ModelAndView modelAndView = new ModelAndView(fileView);
    // return modelAndView;
    // }

}
