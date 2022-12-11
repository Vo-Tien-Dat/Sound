package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;
import com.music.sound.DAO.PlaylistDTO;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.config.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

@Controller
public class FavoriteController {

    @Autowired
    public AlbumDAO albumDAO;

    @Autowired
    public PlaylistDAO playlistDAO;

    @Autowired
    public SoundDAO soundDAO;

    @Autowired
    public UserDAO userDAO;

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public ModelAndView getFavorite(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/favorite/index";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0
                    ? true
                    : false;
            if (isRoleUser) {
                String idUser = roleDTO.getIdUser();
                List<PlaylistDTO> playlists = new ArrayList<>();
                List<AlbumDTO> albums = new ArrayList<>();
                List<SoundDTO> sounds = new ArrayList<>();
                try {

                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    albums = albumDAO.readAllAlbumByIdUserFromFavoriteAlbumUser(idUser);
                    modelAndView.addObject("albums", albums);

                    sounds = soundDAO.readAllSoundByIdUserFromFavoriteSoundUser(idUser);
                    modelAndView.addObject("sounds", sounds);

                    playlists = playlistDAO.readAllPlaylistByIdUserFromFavoritePlaylistUser(idUser);
                    modelAndView.addObject("playlists", playlists);
                    modelAndView.addObject("path_image_user", urlPathImageUser);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectAdmin);
            }

        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }
}
