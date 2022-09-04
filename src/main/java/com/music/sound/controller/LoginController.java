package com.music.sound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.music.sound.model.User;
import com.music.sound.security.CustomAuthenticationProvider;
import com.music.sound.security.JwtTokenUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@Controller
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger("LoginController.class");


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin(HttpServletRequest request){
        logger.info("run view");
        String pathFile = "/page/login/index";
        ModelAndView view = new ModelAndView(pathFile);
        return view;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postLogin(@RequestBody String username, @RequestBody String password){
        System.out.println(username + " " + password);
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        return modelAndView;
    }
}

