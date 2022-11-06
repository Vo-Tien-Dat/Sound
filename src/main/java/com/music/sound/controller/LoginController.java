package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DTO.UserDTO.UserLoginDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.config.Constant;
import com.music.sound.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

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
            @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO, HttpServletRequest request) {

        String pathFile = "/page/login/index";

        ModelAndView modelAndView = new ModelAndView(pathFile);

        String actionButton = request.getParameter("button");

        switch (actionButton) {

            // case: login
            case Constant.ACTION_SIGN_IN:
                break;

            // case: register
            case Constant.ACTION_SIGN_UP:

                Boolean result = authenticationService.createNewUser(userRegisterDTO);

                if (result) {
                    String urlRedirect = "redirect:/home";
                    modelAndView.setViewName(urlRedirect);
                } else {
                    String message = "Không thể đăng kí được tài khoản";
                    modelAndView.addObject("messageSignUp", message);
                }
                break;
            default:
                break;
        }

        return modelAndView;
    }

    // @RequestMapping(value = "/register", method = RequestMethod.POST)
    // public ModelAndView postRegister(@ModelAttribute("userLoginDTO") UserLoginDTO
    // userLoginDTO,
    // @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO) {
    // String pathFile = "/page/login/index";
    // System.out.println(userRegisterDTO.getUsername());

    // String message = authenticationService.createNewUser(userRegisterDTO);
    // System.out.println(message);

    // ModelAndView modelAndView = new ModelAndView(pathFile);
    // return modelAndView;
    // }
}
