package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegister() {
        String pathFile = "/page/register/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("userRegisterDTO", new UserRegisterDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView postRegister(@ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO) {
        String pathFile = "/page/register/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        try {
            System.out.println(userRegisterDTO.getUsername());
            userService.create(userRegisterDTO);
        } catch (Exception ex) {
            modelAndView.addObject("errorMessage", ex.getMessage());
        }
        return modelAndView;
    }
}
