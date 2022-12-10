package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.PlaylistDTO;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;
import com.music.sound.DTO.AlbumDTO.AlbumDTORead;
import com.music.sound.config.Constant;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HomeController {

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private PlaylistDAO playlistDAO;

    @Autowired
    private SoundDAO soundDAO;

    @Autowired
    private UserDAO userDAO;

    /*-------------------------------------------- HOME PAGE --------------------------------------------------------------------- */

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                List<AlbumDTO> albums = new ArrayList<>();
                List<PlaylistDTO> playlists = new ArrayList<>();
                List<SoundDTO> sounds = new ArrayList<>();
                try {
                    // hiển thị user của người dùng
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    sounds = soundDAO.readAllSoundHaveLimit(Constant.LIMIT_SOUND_HOME);

                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    // hiển thị danh sách album
                    albums = albumDAO.readAllAlbumHaveLimit(Constant.LIMIT_ALBUM_HOME);
                    for (AlbumDTO album : albums) {
                        String pathImage = album.getPathImage();
                        String urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        if (pathImage != null) {
                            urlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        album.setPathImage(urlPathImage);
                    }
                    modelAndView.addObject("albums", albums);

                    // hiển thị danh sách playlist
                    playlists = playlistDAO.readAllPLaylistHaveLimit(Constant.LIMIT_PLAYLIST_HOME);
                    for (PlaylistDTO playlist : playlists) {
                        String pathImage = playlist.getPathImage();
                        String urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        if (pathImage != null) {
                            urlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        playlist.setPathImage(urlPathImage);
                    }
                    modelAndView.addObject("playlists", playlists);
                    modelAndView.addObject("sounds", sounds);
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

    // feature: save album in your favorite
    @RequestMapping(value = "/favorite/create/album/{id}", method = RequestMethod.POST)
    public ModelAndView postFavoriteAlbum(@PathVariable("id") String idAlbum,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";

        ModelAndView modelAndView = new ModelAndView(urlRedirectHome);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    albumDAO.createFavoriteAlbumUserByIdAlbumAndIdUser(idAlbum, idUser);
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

    // feature: save playlist in your favorite
    @RequestMapping(value = "/favorite/create/playlist/{id}", method = RequestMethod.POST)
    public ModelAndView postFavoritePlaylist(@PathVariable("id") String idPlaylist, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/index";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";

        ModelAndView modelAndView = new ModelAndView(urlRedirectHome);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    playlistDAO.createFavoritePlaylistUserByIdPlaylistAndIdUser(idPlaylist, idUser);
                    ;
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

    // feature: save sound in your favorite
    @RequestMapping(value = "/favorite/create/sound/{id}", method = RequestMethod.POST)
    public ModelAndView postFavoriteSound(@PathVariable("id") String idSound, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/index";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";

        ModelAndView modelAndView = new ModelAndView(urlRedirectHome);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    soundDAO.createFavoriteSoundUserByIdSoundAndIdUser(idSound, idUser);

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

    @RequestMapping(value = "favorite/delete/album/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteAlbumUserFromAlbumUser(@PathVariable(name = "id") String idAlbum,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/home";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";
        String urlRedirectFavorite = "redirect:/favorite";
        ModelAndView modelAndView = new ModelAndView(urlRedirectFavorite);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    albumDAO.deleteFavoriteAlbumUserByIdAlbumAndIdUser(idAlbum, idUser);
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

    @RequestMapping(value = "/favorite/delete/playlist/{id}", method = RequestMethod.POST)
    public ModelAndView postDeletePlaylistUserFromFavoriteAlbumUser(@PathVariable(name = "id") String idPlaylist,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/index";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";
        String urlRedirectFavorite = "redirect:/favorite";

        ModelAndView modelAndView = new ModelAndView(urlRedirectFavorite);
        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    playlistDAO.deleteFavoritePlaylistUserByIdPlaylistAndIdUser(idPlaylist, idUser);

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {

            }
        } else {
            modelAndView.setViewName(urlRedirectFavorite);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/favorite/delete/sound/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteSoundUserFromFavoriteSoundUser(@PathVariable(name = "id") String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/home/index";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";
        String urlRedirectFavorite = "redirect:/favorite";

        ModelAndView modelAndView = new ModelAndView(urlRedirectFavorite);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    soundDAO.deleteFavoriteSoundUserByIdSoundAndIdUser(idSound, idUser);
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

    // feature: redirect to home
    // @RequestMapping(value = "/album/*", method = RequestMethod.GET)
    // public ModelAndView getRootAlbum() {
    // String urlRedirect = "redirect:/album";
    // ModelAndView modelAndView = new ModelAndView(urlRedirect);
    // return modelAndView;
    // }

    // feature: show item album
    @RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
    public ModelAndView getAlbum(@PathVariable("id") String idAlbum) {
        String fileView = "/page/album/index";
        String urlRediect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(fileView);

        try {
            AlbumDTO album = albumDAO.readAlbumByIdAlbum(idAlbum);
            List<SoundDTO> sounds = soundDAO.readAllSoundByIdAlbum(idAlbum);
            List<AlbumDTO> albums = albumDAO.readAllAlbumHaveLimit(Constant.LIMIT_ALBUM_HOME);
            if (album == null) {
                modelAndView.setViewName(urlRediect);
            } else {
                modelAndView.addObject("album", album);
                modelAndView.addObject("sounds", sounds);
                modelAndView.addObject("albums", albums);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRediect);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/album", method = RequestMethod.GET)
    public ModelAndView getAlbum() {
        String fileView = "/page/album/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: redirect to home
    @RequestMapping(value = "/playlist/*", method = RequestMethod.GET)
    public ModelAndView getRootPlaylist() {
        String urlRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    // @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    // public ModelAndView getPlaylist() {
    // String fileView = "/page/playlist/index1";
    // ModelAndView modelAndView = new ModelAndView(fileView);
    // return modelAndView;
    // }

    // feature: show item playlist
    @RequestMapping(value = "/playlist/{id}", method = RequestMethod.GET)
    public ModelAndView getPlaylist(@PathVariable("id") String idPlaylist, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/playlist/index1";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/*";
        String urlRedirectHome = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0 ? true : false;
            if (isRoleUser) {

                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    PlaylistDTO playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);

                    if (playlist == null) {
                        modelAndView.setViewName(urlRedirectHome);
                    } else {
                        List<SoundDTO> sounds = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylist(idPlaylist);
                        modelAndView.addObject("sounds", sounds);
                        modelAndView.addObject("playlist", playlist);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    modelAndView.setViewName(urlRedirectHome);
                }
            } else {
                modelAndView.setViewName(urlRedirectAdmin);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ModelAndView getUser(@PathVariable("id") String idUser) {
        String fileView = "/page/user/index1";
        ModelAndView modelAndView = new ModelAndView(fileView);
        UserDTO user = new UserDTO();
        try {
            user = userDAO.readUserByIdUser(idUser);
            modelAndView.addObject("user", user);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ModelAndView postUser(@ModelAttribute("user") UserDTO user, HttpServletRequest request) {
        String urlRedirect = "redirect:/home";
        String fileView = "/page/user/index1";
        ModelAndView modelAndView = new ModelAndView();
        String valueActionButton = request.getParameter("button");
        switch (valueActionButton) {
            case "cancel":
                modelAndView.setViewName(urlRedirect);
                break;
            case "update":
                break;
            default:
                break;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logout/{id}", method = RequestMethod.POST)
    public ModelAndView postLogout(@PathVariable("id") String idSession, HttpServletRequest request) {
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView();
        try {
            HttpSession session = request.getSession();
            session.removeAttribute(idSession);
            modelAndView.setViewName(urlRedirectLogin);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRedirectHome);
        }
        return modelAndView;
    }
}
