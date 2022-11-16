package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {

        String pathRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getRoot() {
        String pathRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView redirectAdmin() {
        String urlRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }
}
