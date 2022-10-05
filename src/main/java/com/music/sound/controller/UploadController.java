package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

    @RequestMapping(value = "/upload/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/upload";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload() {
        String path = "/page/upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        modelAndView.addObject("namePage", "Upload");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView postUpload() {
        String path = "/page/upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        modelAndView.addObject("namePage", "Upload");
        return modelAndView;
    }
}
