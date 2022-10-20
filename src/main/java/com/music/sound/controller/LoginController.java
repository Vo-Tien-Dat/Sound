package com.music.sound.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DTO.UserDTO.UserLoginDTO;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin(HttpServletRequest request) {
        String pathFile = "/page/login/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("userLoginDTO", new UserLoginDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView postLogin(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO) {
        String pathFile = "/page/login/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        if (username.length() == 0 || password.length() == 0) {
            modelAndView.addObject("errorMessage", "error");
        }
        return modelAndView;
    }
}
