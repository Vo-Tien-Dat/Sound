package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LibraryController {
    @RequestMapping(value = "/library/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/library/overview";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public ModelAndView getLibrary() {
        String pathRedirect = "redirect:/library/overview";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/library/overview", method = RequestMethod.GET)
    public ModelAndView getOverview() {
        String pathFile = "/page/library/overview/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }

    @RequestMapping(value = "/library/albums", method = RequestMethod.GET)
    public ModelAndView getAlbum() {
        String pathFile = "/page/library/album/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }

    @RequestMapping(value = "/library/playlists", method = RequestMethod.GET)
    public ModelAndView getPlaylist() {
        String pathFile = "/page/library/playlist/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }

    @RequestMapping(value = "/library/sounds", method = RequestMethod.GET)
    public ModelAndView getTracks() {
        String pathFile = "/page/library/track/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }

    @RequestMapping(value = "/library/following", method = RequestMethod.GET)
    public ModelAndView getFollowing() {
        String pathFile = "/page/library/following/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("namePage", "Library");
        return modelAndView;
    }
}
