package com.music.sound.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.service.UserService;
import java.util.List;
import java.util.ArrayList;
import com.music.sound.DTO.SoundDTO.SoundDTO;
import com.music.sound.DTO.UserDTO.UserDTOHome;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome() {
        String pathFile = "/page/home/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);

        List<SoundDTO> sounds = new ArrayList<>();
        sounds.add(new SoundDTO("Hồi duyên", "", "Khởi Vũ"));
        sounds.add(new SoundDTO("Ngưởi có còn thương", "", "Dee Trần"));

        List<UserDTOHome> users = new ArrayList<>();
        users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
        users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
        modelAndView.addObject("sounds", sounds);
        modelAndView.addObject("users", users);
        return modelAndView;
    }
}
