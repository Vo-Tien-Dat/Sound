package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView getTest() {
        String filePath = "/page/test/index";
        ModelAndView modelAndView = new ModelAndView(filePath);
        return modelAndView;
    }
}
