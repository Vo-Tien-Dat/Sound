package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LibraryController {
    @RequestMapping(value = "/library/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathFile = "/page/library/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }

    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public ModelAndView getLibrary() {
        String pathFile = "/page/library/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }
}
