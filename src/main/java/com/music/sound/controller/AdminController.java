package com.music.sound.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;
import java.util.ArrayList;
import com.music.sound.model.User;
import com.music.sound.service.SaveFileUpload;
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

    private SaveFileUpload saveFileUpload;

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public ModelAndView index() {
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    /*------------------------------------------------------- SOLVE: CRUD ALBUM--------------------------------------------- */

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
    public ModelAndView getIdAlbum(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/album/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddAlbum = "redirect:/admin/album/add/";
        String urlRedirectRootAlbum = "redirect:/admin/album";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    String idAlbum = albumDAO.getIdAblbumBeforeCreateAlbum();
                    urlRedirectAddAlbum = urlRedirectAddAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectAddAlbum);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                    modelAndView.setViewName(urlRedirectRootAlbum);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {

        }

        return modelAndView;
    }

    @RequestMapping(value = { "album/add/{id}", "album/editor/{id}" }, method = RequestMethod.GET)
    public ModelAndView getAddAlbum(@PathVariable(value = "id") String idAlbum, HttpSession session) {
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
                try {

                    AlbumDTO album = new AlbumDTO();
                    List<SoundDTO> soundAddedAlbums = new ArrayList<>();
                    List<SoundDTO> sounds = new ArrayList<>();

                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    album = albumDAO.readAlbumByIdAlbum(idAlbum);
                    modelAndView.addObject("album", album);

                    soundAddedAlbums = soundDAO.readAllSoundByIdAlbum(idAlbum);
                    modelAndView.addObject("soundAddedAlbums", soundAddedAlbums);

                    sounds = soundDAO.readAllSoundByIdAlbumIsNull();
                    modelAndView.addObject("sounds", sounds);

                } catch (Exception ex) {
                    String message = ex.getMessage();
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;

    }

    @RequestMapping(value = "album/add/", method = RequestMethod.POST, params = "add_sound")
    public ModelAndView postAddSoundIntoAlbum(
            @RequestParam(value = "id_album", required = false) String idAlbum,
            @RequestParam(value = "name_album", required = false) String nameAlbum,
            @RequestParam(value = "name_singer", required = false) String nameSinger,
            @RequestParam(value = "add_sound", required = false) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddAlbum = "redirect:/admin/album/add/";
        String urlRedirectRootAlbum = "redirect:/admin/album";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {

                try {
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);

                    albumDAO.updateAlbum(album);

                    soundDAO.updateIdAlbumByIdSound(idAlbum, idSound);
                    urlRedirectAddAlbum = urlRedirectAddAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectAddAlbum);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                    modelAndView.setViewName(urlRedirectRootAlbum);
                }

            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/add/", method = RequestMethod.POST, params = "delete_sound")
    public ModelAndView postDeleteSoundFromAlbum(@RequestParam(value = "id_album", required = false) String idAlbum,
            @RequestParam(value = "name_album", required = false) String nameAlbum,
            @RequestParam(value = "name_singer", required = false) String nameSinger,
            @RequestParam(value = "delete_sound", required = false) String idSound,
            HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;
        ModelAndView modelAndView = new ModelAndView();

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddAlbum = "redirect:/admin/album/add/";

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                AlbumDTO album = new AlbumDTO();
                album.setIdAlbum(idAlbum);
                album.setNameAlbum(nameAlbum);
                album.setNameSinger(nameSinger);

                albumDAO.updateAlbum(album);
                soundDAO.updateIdAlbumByIdSound(null, idSound);

                urlRedirectAddAlbum = urlRedirectAddAlbum + idAlbum;
                modelAndView.setViewName(urlRedirectAddAlbum);
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.POST, params = "add_album")
    public ModelAndView postAddAlbum(@RequestParam(value = "id_album", required = false) String idAlbum,
            @RequestParam(value = "name_album", required = false) String nameAlbum,
            @RequestParam(value = "name_singer", required = false) String nameSinger,
            @RequestParam(value = "add_sound", required = false) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectRootAlbum = "redirect:/admin/album";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);
                    albumDAO.updateAlbum(album);

                    modelAndView.setViewName(urlRedirectRootAlbum);

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                    modelAndView.setViewName(urlRedirectRootAlbum);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "album/add", method = RequestMethod.POST, params = "cancel_album")
    public ModelAndView postCancelAlbum(@RequestParam(value = "id_album", required = false) String idAlbum,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectRootAlbum = "redirect:/admin/album";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    soundDAO.updateIdAlbumIsNullByIdAlbumFromSound(idAlbum);
                    albumDAO.deleteAlbumByIdAlbum(idAlbum);

                    modelAndView.setViewName(urlRedirectRootAlbum);
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                    modelAndView.setViewName(urlRedirectRootAlbum);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
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

    /*------------------------------------------------------- SOLVE: CRUD PLAYLIST --------------------------------------------- */

    // feature: add playlist
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

    // feature: get id playlist and redirect to add playlist
    @RequestMapping(value = "playlist/add", method = RequestMethod.GET)
    public ModelAndView getIdPlaylist() {

        String urlRedirect = "redirect:/admin/playlist/add/";
        String urlRedirectRootPlaylist = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView();
        try {
            String idPlaylist = playlistDAO.getIdPlaylistBeforeCreatePlaylist();
            urlRedirect = urlRedirect + idPlaylist;
            modelAndView.setViewName(urlRedirect);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRedirectRootPlaylist);
        }

        return modelAndView;
    }

    // feature: show page playlist
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

                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    modelAndView.addObject("playlist", playlist);

                    sounds = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylistFirst(idPlaylist);
                    modelAndView.addObject("sounds", sounds);

                    soundAddedPlaylists = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylist(idPlaylist);
                    modelAndView.addObject("soundAddedPlaylists", soundAddedPlaylists);
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

    // feature: add sound into playlist
    @RequestMapping(value = { "playlist/add/", "playlist/editor/" }, method = RequestMethod.POST, params = "add_sound")
    public ModelAndView postAddSoundIntoPlaylist(
            @RequestParam(value = "name_playlist", required = false) String namePlaylist,
            @RequestParam(value = "id_playlist", required = false) String idPlaylist,
            @RequestParam(value = "add_sound", required = false) String idSound) {
        String urlRedirect = "redirect:/admin/playlist/add/";
        urlRedirect = urlRedirect + idPlaylist;
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            PlaylistDTO playlist = new PlaylistDTO();
            playlist.setIdPlaylist(idPlaylist);
            playlist.setNamePlaylist(namePlaylist);
            playlistDAO.updatePlaylist(playlist);
            playlistDAO.createSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }

        return modelAndView;
    }

    // feature: delete sound from playlist
    @RequestMapping(value = { "playlist/add/",
            "playlist/editor/" }, method = RequestMethod.POST, params = "delete_sound")
    public ModelAndView postDeleteSoundFromPlaylist(@RequestParam("name_playlist") String namePlaylist,
            @RequestParam("id_playlist") String idPlaylist,
            @RequestParam("delete_sound") String idSound) {
        String urlRedirect = "redirect:/admin/playlist/add/";
        urlRedirect = urlRedirect + idPlaylist;
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        try {
            PlaylistDTO playlist = new PlaylistDTO();
            playlist.setIdPlaylist(idPlaylist);
            playlist.setNamePlaylist(namePlaylist);
            playlistDAO.updatePlaylist(playlist);
            playlistDAO.deleteSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // feature: cancel add playlist
    @RequestMapping(value = "playlist/add/", method = RequestMethod.POST, params = "cancel_playlist")
    public ModelAndView postCancelPlaylist(@RequestParam("id_playlist") String idPlaylist) {
        String urlRedirectPlaylist = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirectPlaylist);
        try {
            playlistDAO.deletePlaylist(idPlaylist);
            playlistDAO.deleteSoundPlaylistByIdPlaylist(idPlaylist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // feature: update final playlist and update editor playlist
    @RequestMapping(value = { "playlist/add/",
            "playlist/editor/" }, method = RequestMethod.POST, params = "add_playlist")
    public ModelAndView postAddPlaylist(@RequestParam("name_playlist") String namePlaylist,
            @RequestParam("id_playlist") String idPlaylist) {
        String urlRedirectPlaylist = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirectPlaylist);
        try {
            PlaylistDTO playlist = new PlaylistDTO();
            playlist.setIdPlaylist(idPlaylist);
            playlist.setNamePlaylist(namePlaylist);
            playlistDAO.updatePlaylist(playlist);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // feature: update playlist
    @RequestMapping(value = { "playlist/editor/{id}" }, method = RequestMethod.GET)
    public ModelAndView getEditorPlaylist(@PathVariable("id") String idPlaylist, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/editor";
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
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    modelAndView.addObject("playlist", playlist);

                    sounds = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylistFirst(idPlaylist);
                    modelAndView.addObject("sounds", sounds);

                    soundAddedPlaylists = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylist(idPlaylist);
                    modelAndView.addObject("soundAddedPlaylists", soundAddedPlaylists);

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

    // feature: cancel editor playlist
    @RequestMapping(value = "playlist/editor/", method = RequestMethod.POST, params = "cancel_playlist")
    public ModelAndView postCancelEditorPlaylist(@RequestParam("id_playlist") String idPlaylist) {
        String urlRedirectPlaylist = "redirect:/admin/playlist";
        ModelAndView modelAndView = new ModelAndView(urlRedirectPlaylist);
        return modelAndView;
    }

    // feature: delete playlist
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

    /*------------------------------------------------------- SOLVE: CRUD SOUND --------------------------------------------- */

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
    public ModelAndView postAddSound(@RequestParam("avatar") MultipartFile fileImage,
            @RequestParam("audio") MultipartFile fileAudio,
            @ModelAttribute("sound") Sound sound, HttpServletRequest request) {

        String urlRedirect = "redirect:/admin/sound";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        String valueActionButton = request.getParameter("button");
        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                try {
                    String nameFileImage = fileImage.getOriginalFilename();
                    String nameFileAudio = fileAudio.getOriginalFilename();
                    String idSound = soundDAO.getIdSoundBeforeInsert(sound);

                    // feature: save file image (avatar sound)
                    saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, nameFileImage, idSound,
                            fileImage);
                    saveFileUpload.save();

                    // feature: save file audio (audo sound)
                    saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_AUDIO, nameFileAudio, idSound,
                            fileAudio);
                    saveFileUpload.save();

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
    public ModelAndView postAddUser(@RequestParam(value = "avatar") MultipartFile file,
            @ModelAttribute("user") User user, HttpServletRequest request) {
        String urlRedirect = "redirect:/admin/user";
        String valueActionButton = request.getParameter("button");
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        String idUserRoot = null;
        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                try {
                    String idUser = userDAO.getIdUserWhileCreateUser(user);
                    String nameFileOld = file.getOriginalFilename();

                    saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, nameFileOld, idUser, file);
                    saveFileUpload.save();
                    idUserRoot = idUser;

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                    userDAO.deleteUserByIdUser(idUserRoot);
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
