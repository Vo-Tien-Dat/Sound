package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @RequestMapping(value = { "/user/*", "/user" }, method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/user/tracks";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/user/albums", method = RequestMethod.GET)
    public ModelAndView getAlbums() {
        String path = "/page/user/albums/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    @RequestMapping(value = "/user/tracks", method = RequestMethod.GET)
    public ModelAndView getTracks() {
        String path = "/page/user/tracks/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    @RequestMapping(value = "/user/playlists", method = RequestMethod.GET)
    public ModelAndView getPlaylists() {
        String path = "/page/user/playlists/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

}
