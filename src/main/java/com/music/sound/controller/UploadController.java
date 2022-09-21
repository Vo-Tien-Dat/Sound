package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload(){
        String path = "/page/upload/index"; 
        ModelAndView modelAndView = new ModelAndView(path); 
        return modelAndView; 
    }
}
