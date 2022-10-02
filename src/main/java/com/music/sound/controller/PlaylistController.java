package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlaylistController {

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
        return modelAndView;
    }

    @RequestMapping(value = "/playlist/{id}", method = RequestMethod.GET)
    public ModelAndView getPlaylistId() {
        String pathFile = "/page/playlist_id/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }
}
