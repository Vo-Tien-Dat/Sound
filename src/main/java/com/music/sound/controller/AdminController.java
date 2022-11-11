package com.music.sound.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.TypeSoundDAO;
import com.music.sound.DAO.TypeSoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;
import java.util.ArrayList;
import com.music.sound.model.User;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TypeSoundDAO typeSoundDAO;

    @Autowired
    private SoundDAO soundDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index() {

        String fileView = "/page/admin/index";

        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // all feature CRUD Album

    @RequestMapping(value = "album", method = RequestMethod.GET)
    public ModelAndView getIndexAlbum() {
        String fileView = "/page/admin/album/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.GET)
    public ModelAndView getAddAlbum() {
        String fileView = "/page/admin/album/add";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.POST)
    public ModelAndView postAddAlbum(HttpServletRequest request) {
        String fileView = "/page/admin/album/add";
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(fileView);

        String valueButtonAction = request.getParameter("button");
        switch (valueButtonAction) {
            case "cancel":
                modelAndView.setViewName(urlRedirect);
                break;
            case "add":
                break;

            default:
                break;
        }
        return modelAndView;
    }

    @RequestMapping(value = "album/edit", method = RequestMethod.GET)
    public ModelAndView getEditAlbum() {
        String fileView = "page/admin/album/edit";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "album/edit", method = RequestMethod.POST)
    public ModelAndView postEditAlbum(HttpServletRequest request) {
        String fileView = "/page/admin/album/add";
        ModelAndView modelAndView = new ModelAndView(fileView);
        String valueActionButton = request.getParameter("button");

        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                break;
            default:
                break;

        }

        return modelAndView;
    }

    @RequestMapping(value = "album/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteAlbum(@PathVariable(name = "id") String idAlbum) {
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    // all feature CRUD PLaylist

    @RequestMapping(value = "playlist", method = RequestMethod.GET)
    public ModelAndView getIndexPlaylist() {
        String fileView = "/page/admin/playlist/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "playlist/add", method = RequestMethod.GET)
    public ModelAndView getAddPlaylist() {
        String fileView = "/page/admin/playlist/add";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "playlist/add", method = RequestMethod.POST)
    public ModelAndView postAddPlaylist() {
        String fileView = "/page/admin/playlist/add";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "playlist/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getEditPlaylist(@PathVariable(name = "id") String idPlaylist) {
        String fileView = "/page/admin/playlist/edit";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "playlist/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeletePlaylist(@PathVariable(name = "id") String idPlaylist) {
        String urlRedirect = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    // all feature CRUD Sound

    @RequestMapping(value = "sound", method = RequestMethod.GET)
    public ModelAndView getIndexSound() {
        String fileView = "/page/admin/sound/index1";
        List<TypeSound> typeSounds = new ArrayList<TypeSound>();
        // List<Sound> sounds = new ArrayList<Sound>();
        List<SoundDTO> sounds = new ArrayList<>();
        try {
            typeSounds = typeSoundDAO.findAllTypeSound();
            sounds = soundDAO.readAllSound();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView(fileView);

        modelAndView.addObject("typeSounds", typeSounds);
        modelAndView.addObject("sound", new Sound());
        modelAndView.addObject("sounds", sounds);

        return modelAndView;
    }

    @RequestMapping(value = "sound/add", method = RequestMethod.GET)
    public ModelAndView getAddSound() {
        String fileView = "/page/admin/sound/add";
        ModelAndView modelAndView = new ModelAndView(fileView);

        List<TypeSoundDTO> typeSounds = new ArrayList<>();
        Sound sound = new Sound();
        try {
            typeSounds = typeSoundDAO.readAllTypeSound();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        modelAndView.addObject("typeSounds", typeSounds);
        modelAndView.addObject("sound", sound);
        return modelAndView;
    }

    @RequestMapping(value = "sound/add", method = RequestMethod.POST)
    public ModelAndView postAddSound(@RequestParam("audio") MultipartFile fileAudio,
            @ModelAttribute("sound") Sound sound, HttpServletRequest request) {

        String urlRedirect = "redirect:/admin/sound";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        String valueActionButton = request.getParameter("button");
        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                try {
                    String nameFileAudio = soundDAO.readIdSoundBeforeCreateSound(sound);
                    System.out.println(nameFileAudio);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            default:
                break;
        }

        return modelAndView;
    }

    @RequestMapping(value = "sound/editor/{id}", method = RequestMethod.GET)
    public ModelAndView getEditSound(@PathVariable("id") String idSound) {
        String fileView = "/page/admin/sound/editor";
        ModelAndView modelAndView = new ModelAndView(fileView);
        Sound sound = soundDAO.findSoundByIdSound(idSound);

        modelAndView.addObject("sound", sound);
        return modelAndView;
    }

    @RequestMapping(value = "sound/editor", method = RequestMethod.POST)
    public ModelAndView postEditSound(@ModelAttribute("sound") Sound sound) {
        String fileView = "/page/admin/sound/editor";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    @RequestMapping(value = "sound/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteSound(@PathVariable("id") String idSound) {
        String urlRedirect = "redirect:/admin/sound";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            soundDAO.deleteSoundByIdSound(idSound);
            modelAndView.addObject("message", "delete success");
        } catch (Exception ex) {
            String message = ex.getMessage();
            modelAndView.addObject("message", message);
        }
        return modelAndView;
    }

    // all feature CRUD User

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ModelAndView getIndexUser() {
        String fileView = "/page/admin/user/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        List<UserDTO> users = new ArrayList<>();
        try {
            users = userDAO.readAllUser();
            modelAndView.addObject("users", users);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    @RequestMapping(value = "user/add", method = RequestMethod.GET)
    public ModelAndView getAddUser() {
        String fileView = "/page/admin/user/add";
        ModelAndView modelAndView = new ModelAndView(fileView);
        User user = new User();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "user/add", method = RequestMethod.POST)
    public ModelAndView postAddUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        String urlRedirect = "redirect:/admin/user";
        String valueActionButton = request.getParameter("button");
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                try {
                    userDAO.insertUser(user);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
                break;
            default:
                break;
        }

        return modelAndView;
    }

    @RequestMapping(value = "user/editor/{id}", method = RequestMethod.GET)
    public ModelAndView getEditorUser(@PathVariable("id") String idUser) {
        String fileView = "/page/admin/user/editor";
        ModelAndView modelAndView = new ModelAndView(fileView);
        try {
            UserDTO user = userDAO.readUserByIdUser(idUser);
            modelAndView.addObject("user", user);
        } catch (Exception ex) {
            String message = ex.getMessage();
        }
        return modelAndView;
    }

    @RequestMapping(value = "user/editor", method = RequestMethod.POST)
    public ModelAndView postEditorUser(@RequestParam("avatar") MultipartFile fileAvatar,
            @ModelAttribute("user") UserDTO user, HttpServletRequest request) {
        String urlRedirect = "redirect:/admin/user";
        String fileView = "/page/admin/user/editor";
        String valueActionButton = request.getParameter("button");
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        switch (valueActionButton) {
            case "cancel":
                break;
            case "update":
                try {
                    userDAO.updateUser(user);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    modelAndView = new ModelAndView(fileView);
                    System.out.println(message);
                }
                break;
            default:
                break;
        }
        return modelAndView;
    }

    @RequestMapping(value = "user/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteUser(@PathVariable("id") String idUser) {
        String urlRedirect = "redirect:/admin/user";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            userDAO.deleteUserByIdUser(idUser);
            modelAndView.addObject("message", "delete success");
        } catch (Exception ex) {
            String message = ex.getMessage();
            modelAndView.addObject("message", message);
        }
        System.out.println("oke");
        return modelAndView;
    }

}
