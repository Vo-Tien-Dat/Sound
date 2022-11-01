package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DTO.UserDTO.UserLoginDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin() {
        String pathFile = "/page/login/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("userLoginDTO", new UserLoginDTO());
        modelAndView.addObject("userRegisterDTO", new UserRegisterDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView postLogin(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
            @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO) {
        String pathFile = "/page/login/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        return modelAndView;
    }
}
