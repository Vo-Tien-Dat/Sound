package com.music.sound.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.apache.catalina.startup.Catalina;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.PlaylistDTO;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.TypeSoundDAO;
import com.music.sound.DAO.TypeSoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.music.sound.model.Album;
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;
import java.util.ArrayList;
import com.music.sound.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.config.Constant;

@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TypeSoundDAO typeSoundDAO;

    @Autowired
    private SoundDAO soundDAO;

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private PlaylistDAO playlistDAO;

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public ModelAndView index() {
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    // all feature CRUD Album

    @RequestMapping(value = "album", method = RequestMethod.GET)
    public ModelAndView getIndexAlbum(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/album/index";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                List<AlbumDTO> albums = new ArrayList<>();
                String idUser = roleDTO.getIdUser();
                try {
                    albums = albumDAO.readAllAlbum();
                    if (albums.size() == 0) {
                        albums = null;
                    }

                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    modelAndView.addObject("albums", albums);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.GET)
    public ModelAndView getAddAlbum(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/album/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();

                UserDTO user = userDAO.readUserByIdUser(idUser);

                String nameUser = user.getNameUser();

                modelAndView.addObject("session_id", idSession);
                modelAndView.addObject("name_user", nameUser);

                Album album = new Album();
                modelAndView.addObject("album", album);

            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.POST)
    public ModelAndView postAddAlbum(@ModelAttribute("album") Album album, HttpServletRequest request) {
        String fileView = "/page/admin/album/add";
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(fileView);

        String valueButtonAction = request.getParameter("button");
        switch (valueButtonAction) {
            case "cancel":
                modelAndView.setViewName(urlRedirect);
                break;
            case "add":
                try {
                    String idAlbum = albumDAO.getIdAlbumBeforeCreateAlbum(album);
                    System.out.println(idAlbum);
                    modelAndView.setViewName(urlRedirect);
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

    @RequestMapping(value = "album/editor/{id}", method = RequestMethod.GET)
    public ModelAndView getEditAlbum(@PathVariable("id") String idAlbum, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "page/admin/album/editor";
        String urlRedirect = "redirect:/admin/album";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();
                try {
                    AlbumDTO album = albumDAO.readAlbumByIdAlbum(idAlbum);
                    if (album == null) {
                        modelAndView.setViewName(urlRedirect);
                    }

                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("album", album);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/editor", method = RequestMethod.POST)
    public ModelAndView postEditAlbum(@ModelAttribute("album") AlbumDTO album, HttpServletRequest request) {
        String fileView = "/page/admin/album/editor";
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(fileView);
        String valueActionButton = request.getParameter("button");
        switch (valueActionButton) {
            case "cancel":
                modelAndView.setViewName(urlRedirect);
                break;
            case "update":
                try {
                    albumDAO.updateAlbum(album);
                    modelAndView.setViewName(urlRedirect);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
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
        try {
            albumDAO.deleteAlbumByIdAlbum(idAlbum);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // all feature CRUD PLaylist

    @RequestMapping(value = "playlist", method = RequestMethod.GET)
    public ModelAndView getIndexPlaylist(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/index";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();

                List<PlaylistDTO> playlists = new ArrayList<>();
                try {
                    playlists = playlistDAO.readAllPLaylist();
                    if (playlists.size() == 0) {
                        playlists = null;
                    }

                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("playlists", playlists);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "playlist/add", method = RequestMethod.GET)
    public ModelAndView getIdPlaylist() {

        String urlRedirect = "redirect:/admin/playlist/add/";
        ModelAndView modelAndView = new ModelAndView();
        try {
            String idPlaylist = playlistDAO.getIdPlaylistBeforeCreatePlaylist();
            urlRedirect = urlRedirect + idPlaylist;
            modelAndView.setViewName(urlRedirect);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }

        return modelAndView;
    }

    @RequestMapping(value = "playlist/add/{id}", method = RequestMethod.GET)
    public ModelAndView getAddPlaylist(@PathVariable("id") String idPlaylist, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                PlaylistDTO playlist = new PlaylistDTO();
                List<SoundDTO> soundAddedPlaylists = new ArrayList<>();
                List<SoundDTO> sounds = new ArrayList<>();
                String idUser = roleDTO.getIdUser();

                try {
                    playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    sounds = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylistFirst(idPlaylist);
                    soundAddedPlaylists = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylist(idPlaylist);

                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("playlist", playlist);
                    modelAndView.addObject("soundAddedPlaylists", soundAddedPlaylists);
                    modelAndView.addObject("sounds", sounds);

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }

        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "playlist/add/{id_playlist}/{id_sound}", method = RequestMethod.POST)
    public ModelAndView postAddSoundIntoPlaylist(
            @PathVariable("id_playlist") String idPlaylist,
            @PathVariable("id_sound") String idSound) {
        String urlRedirect = "redirect:/admin/playlist/add/";
        urlRedirect = urlRedirect + idPlaylist;
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            playlistDAO.createSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }

        return modelAndView;
    }

    @RequestMapping(value = "playlist/delete/{id_playlist}/{id_sound}", method = RequestMethod.POST)
    public ModelAndView postDeleteSoundFromPlaylist(
            @PathVariable("id_playlist") String idPlaylist,
            @PathVariable("id_sound") String idSound) {
        String urlRedirect = "redirect:/admin/playlist/add/";
        urlRedirect = urlRedirect + idPlaylist;
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            playlistDAO.deleteSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }

        return modelAndView;
    }

    @RequestMapping(value = "playlist/add", method = RequestMethod.POST)
    public ModelAndView postAddPlaylist(@ModelAttribute("playlist") PlaylistDTO playlist, HttpServletRequest request) {
        String fileView = "/page/admin/playlist/add";
        String urlRedirect = "redirect:/admin/playlist";
        String valueButtonAction = request.getParameter("button");
        ModelAndView modelAndView = new ModelAndView(fileView);
        switch (valueButtonAction) {
            case "cancel":
                String idPlaylist = playlist.getIdPlaylist();
                playlistDAO.deletePlaylist(idPlaylist);
                playlistDAO.deleteSoundPlaylistByIdPlaylist(idPlaylist);
                modelAndView.setViewName(urlRedirect);
                break;
            case "add":
                try {
                    playlistDAO.updatePlaylist(playlist);
                    modelAndView.setViewName(urlRedirect);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
                break;
            default:
                break;
        }
        try {

        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    @RequestMapping(value = "playlist/editor/{id}", method = RequestMethod.GET)
    public ModelAndView getEditPlaylist(@PathVariable(name = "id") String idPlaylist) {
        String fileView = "/page/admin/playlist/editor";
        ModelAndView modelAndView = new ModelAndView(fileView);
        try {
            PlaylistDTO playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
            // for (SoundDTO sound : sounds) {
            // System.out.println(sound.getIdSound() + " " + sound.getNameSound());
            // }
            modelAndView.addObject("playlist", playlist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    @RequestMapping(value = "playlist/editor", method = RequestMethod.POST)
    public ModelAndView postEditPlaylist(@ModelAttribute("playlist") PlaylistDTO playlist, HttpServletRequest request) {
        String urlRedirect = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        String valueActionButton = request.getParameter("button");
        switch (valueActionButton) {
            case "cancel":
                modelAndView.setViewName(urlRedirect);
                break;
            case "update":
                try {
                    modelAndView.setViewName(urlRedirect);
                    playlistDAO.updatePlaylist(playlist);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
                break;
            default:
                break;
        }
        try {

        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    @RequestMapping(value = "playlist/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeletePlaylist(@PathVariable(name = "id") String idPlaylist) {
        String urlRedirect = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            playlistDAO.deletePlaylist(idPlaylist);
            playlistDAO.deleteSoundPlaylistByIdPlaylist(idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // all feature CRUD Sound

    @RequestMapping(value = "sound", method = RequestMethod.GET)
    public ModelAndView getIndexSound(HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/index1";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();
                List<TypeSound> typeSounds = new ArrayList<TypeSound>();
                // List<Sound> sounds = new ArrayList<Sound>();
                List<SoundDTO> sounds = new ArrayList<>();
                try {
                    typeSounds = typeSoundDAO.findAllTypeSound();
                    sounds = soundDAO.readAllSound();
                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                modelAndView.addObject("typeSounds", typeSounds);
                modelAndView.addObject("sound", new Sound());
                modelAndView.addObject("sounds", sounds);

            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "sound/add", method = RequestMethod.GET)
    public ModelAndView getAddSound(HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();
                List<TypeSoundDTO> typeSounds = new ArrayList<>();
                Sound sound = new Sound();
                try {
                    typeSounds = typeSoundDAO.readAllTypeSound();
                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("typeSounds", typeSounds);
                    modelAndView.addObject("sound", sound);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

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
    public ModelAndView getEditorSound(@PathVariable("id") String idSound, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/editor";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    String idUser = roleDTO.getIdUser();

                    Sound sound = soundDAO.findSoundByIdSound(idSound);
                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("sound", sound);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);

                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "sound/editor", method = RequestMethod.POST)
    public ModelAndView postEditorSound(@ModelAttribute("sound") Sound sound, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/editor";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {

            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

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
    public ModelAndView getIndexUser(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/user/index";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                List<UserDTO> users = new ArrayList<>();
                try {
                    String idUser = roleDTO.getIdUser();
                    users = userDAO.readAllUser();
                    modelAndView.addObject("users", users);

                    UserDTO user = userDAO.readUserByIdUser(idUser);

                    String nameUser = user.getNameUser();

                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "user/add", method = RequestMethod.GET)
    public ModelAndView getAddUser(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/user/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String idUser = roleDTO.getIdUser();
                User user = new User();
                UserDTO userDTO = userDAO.readUserByIdUser(idUser);

                String nameUser = userDTO.getNameUser();

                modelAndView.addObject("session_id", idSession);
                modelAndView.addObject("name_user", nameUser);
                modelAndView.addObject("user", user);
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

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
    public ModelAndView getEditorUser(@PathVariable("id") String idUser, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/user/editor";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    modelAndView.addObject("user", user);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
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
        return modelAndView;
    }

}
