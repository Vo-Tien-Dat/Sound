package com.music.sound.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.PlaylistDTO;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.TypeSoundDAO;
import com.music.sound.DAO.TypeSoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;
import com.music.sound.config.Constant;
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;
import com.music.sound.model.User;
import com.music.sound.service.DeleteFile;
import com.music.sound.service.SaveFileUpload;
import org.springframework.dao.DuplicateKeyException;

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

        String fileView = "/page/admin/album/index1";
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
                    String imageNameFile = user.getIdUser();

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

    @RequestMapping(value = "album/editor", method = RequestMethod.POST)
    public ModelAndView postAddAndEditAlbum(
            @RequestParam(value = "album_name", required = true) String nameAlbum,
            @RequestParam(value = "singer_name", required = true) String nameSinger, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectRootAlbum = "redirect:/admin/album";
        String urlRedirectEditorAlbum = "redirect:/admin/album/editor/";
        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    String idAlbum = albumDAO.getIdAblbumBeforeCreateAlbum();
                    AlbumDTO album = new AlbumDTO();
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);
                    albumDAO.updateAlbum2Arg(album);
                    urlRedirectEditorAlbum = urlRedirectEditorAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectEditorAlbum);
                } catch (Exception ex) {
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

                    String pathImage = album.getPathImage();
                    String urlAvatarImage = null;
                    if (pathImage != null) {
                        urlAvatarImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }
                    modelAndView.addObject("urlAvatarImage", urlAvatarImage);

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

    @RequestMapping(value = "album/add/", method = RequestMethod.POST, params = "add_sound")
    public ModelAndView postAddSoundIntoAlbum(
            @RequestParam(value = "avatar") MultipartFile fileImage,
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
                    AlbumDTO oldAlbum = albumDAO.readAlbumByIdAlbum(idAlbum);
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldAlbum.getPathImage();
                    // lưu file ảnh vào

                    if (fileSize != 0) {
                        String prefixFileName = idAlbum;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        album.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        album.setPathImage(oldPathImage);
                    }

                    // cập nhật thông tin album
                    albumDAO.updateAlbum(album);

                    // cập nhật bài hát thuộc của album nay
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
    public ModelAndView postDeleteSoundFromAlbum(
            @RequestParam(value = "avatar") MultipartFile fileImage,
            @RequestParam(value = "id_album", required = false) String idAlbum,
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
                try {
                    AlbumDTO oldAlbum = albumDAO.readAlbumByIdAlbum(idAlbum);
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldAlbum.getPathImage();

                    // lưu file ảnh vào
                    if (fileSize != 0) {
                        String prefixFileName = idAlbum;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        album.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        album.setPathImage(oldPathImage);
                    }

                    albumDAO.updateAlbum(album);
                    soundDAO.updateIdAlbumByIdSound(null, idSound);

                    urlRedirectAddAlbum = urlRedirectAddAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectAddAlbum);
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

    @RequestMapping(value = "album/add", method = RequestMethod.POST, params = "add_album")
    public ModelAndView postAddAlbum(
            @RequestParam(value = "avatar") MultipartFile fileImage,
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
        String urlRedirectRootAlbum = "redirect:/admin/album";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    AlbumDTO oldAlbum = albumDAO.readAlbumByIdAlbum(idAlbum);
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);
                    albumDAO.updateAlbum(album);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldAlbum.getPathImage();

                    // lưu file ảnh vào
                    if (fileSize != 0) {
                        String prefixFileName = idAlbum;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        album.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        album.setPathImage(oldPathImage);
                    }

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
    public ModelAndView postDeleteAlbum(@PathVariable(name = "id") String idAlbum, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirect = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    albumDAO.deleteAlbumByIdAlbum(idAlbum);
                    soundDAO.updateIdAlbumIsNullByIdAlbumFromSound(idAlbum);
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

    /*------------------------------------------------------- SOLVE: CRUD PLAYLIST --------------------------------------------- */

    // feature: add playlist
    @RequestMapping(value = "playlist", method = RequestMethod.GET)
    public ModelAndView getIndexPlaylist(HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/index1";
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

    // hiển thị trang thêm playlist
    @RequestMapping(value = { "playlist/add/{id}", "playlist/editor/{id}" }, method = RequestMethod.GET)
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

                    String pathImage = playlist.getPathImage();
                    String urlAvatarImage = null;
                    if (pathImage != null) {
                        urlAvatarImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }
                    modelAndView.addObject("urlAvatarImage", urlAvatarImage);
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
            @RequestParam(value = "avatar") MultipartFile fileImage,
            @RequestParam(value = "name_playlist", required = false) String namePlaylist,
            @RequestParam(value = "id_playlist", required = false) String idPlaylist,
            @RequestParam(value = "add_sound", required = false) String idSound,
            HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddPlaylist = "redirect:/admin/playlist/add/";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    PlaylistDTO oldPlaylist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);
                    playlist.setNamePlaylist(namePlaylist);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldPlaylist.getPathImage();

                    if (fileSize != 0) {
                        String prefixFileName = idPlaylist;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        playlist.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        playlist.setPathImage(oldPathImage);
                    }

                    playlistDAO.updatePlaylist(playlist);
                    playlistDAO.createSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
                    urlRedirectAddPlaylist = urlRedirectAddPlaylist + idPlaylist;
                    modelAndView.setViewName(urlRedirectAddPlaylist);
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

    // feature: delete sound from playlist
    @RequestMapping(value = { "playlist/add/",
            "playlist/editor/" }, method = RequestMethod.POST, params = "delete_sound")
    public ModelAndView postDeleteSoundFromPlaylist(
            @RequestParam(value = "avatar") MultipartFile fileImage,
            @RequestParam(value = "name_playlist") String namePlaylist,
            @RequestParam(value = "id_playlist") String idPlaylist,
            @RequestParam(value = "delete_sound") String idSound, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddPlaylist = "redirect:/admin/playlist/add/";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    PlaylistDTO oldPlaylist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);
                    playlist.setNamePlaylist(namePlaylist);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldPlaylist.getPathImage();

                    if (fileSize != 0) {
                        String prefixFileName = idPlaylist;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        playlist.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        playlist.setPathImage(oldPathImage);
                    }

                    playlistDAO.updatePlaylist(playlist);
                    playlistDAO.deleteSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
                    urlRedirectAddPlaylist = urlRedirectAddPlaylist + idPlaylist;
                    modelAndView.setViewName(urlRedirectAddPlaylist);
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
    public ModelAndView postAddPlaylist(
            @RequestParam(value = "avatar") MultipartFile fileImage,
            @RequestParam(value = "name_playlist") String namePlaylist,
            @RequestParam(value = "id_playlist") String idPlaylist, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAddPlaylist = "redirect:/admin/playlist/add/";
        String urlRedirectRootPlaylist = "redirect:/admin/playlist/";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    PlaylistDTO oldPlaylist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);
                    playlist.setNamePlaylist(namePlaylist);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldPlaylist.getPathImage();

                    if (fileSize != 0) {
                        String prefixFileName = idPlaylist;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        playlist.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        playlist.setPathImage(oldPathImage);
                    }

                    playlistDAO.updatePlaylist(playlist);

                    modelAndView.setViewName(urlRedirectRootPlaylist);
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

        String fileView = "/page/admin/sound/index";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {

                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    List<SoundDTO> sounds = new ArrayList<>();
                    sounds = soundDAO.readAllSound();
                    for (SoundDTO sound : sounds) {
                        String pathImage = sound.getPathImage();
                        String imageDefault = "/assets/img/default/sound_default.png";
                        String newPathImage = imageDefault;
                        if (pathImage != null) {
                            newPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        sound.setPathImage(newPathImage);
                    }
                    modelAndView.addObject("sounds", sounds);

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

                try {

                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    // lấy danh sach thể loại bài hát
                    List<TypeSoundDTO> typeSounds = new ArrayList<>();
                    typeSounds = typeSoundDAO.readAllTypeSound();
                    modelAndView.addObject("typeSounds", typeSounds);

                    Sound sound = new Sound();
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
    public ModelAndView postAddSound(
            @RequestParam(value = "avatar", required = false) MultipartFile fileImage,
            @RequestParam(value = "audio", required = false) MultipartFile fileAudio,
            @ModelAttribute("sound") Sound sound, HttpServletRequest request, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        String urlRedirect = "redirect:/admin/sound";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                String valueActionButton = request.getParameter("button");
                String idSound = soundDAO.getIdSoundBeforeInsert(sound);
                switch (valueActionButton) {
                    case "cancel":
                        try {
                            soundDAO.deleteSoundByIdSound(idSound);
                        } catch (Exception ex) {

                        }

                        break;
                    case "add":
                        try {
                            Long sizeFileAudio = fileAudio.getSize();
                            if (sizeFileAudio == 0) {
                                throw new NullPointerException("KHONG TIM THAY FILE AUDIO");
                            }

                            String prefixFileName = idSound;

                            // chức năng lưu file ảnh vào thư mục /assets/img/data
                            Long sizeFileImage = fileImage.getSize();
                            if (sizeFileImage != 0) {
                                saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage,
                                        prefixFileName);

                                saveFileUpload.setFullFileName();
                                saveFileUpload.commit();
                                String fullFileNameImage = saveFileUpload.getFullFileName();
                                soundDAO.updatePathImageByIdSound(idSound, fullFileNameImage);
                            }

                            // lưu file âm nhạc vào thừ mục /assets/audio/data
                            saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_AUDIO, fileAudio,
                                    prefixFileName);
                            saveFileUpload.setFullFileName();
                            saveFileUpload.commit();
                            String fullFileNameAudio = saveFileUpload.getFullFileName();
                            soundDAO.updatePathAudioByIdSound(idSound, fullFileNameAudio);

                        } catch (NullPointerException ex) {
                            String message = "File Audio is null";
                            modelAndView.setViewName(fileView);
                            modelAndView.addObject("message", message);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            try {
                                soundDAO.deleteSoundByIdSound(idSound);
                            } catch (Exception ex1) {
                                ex1.printStackTrace();
                            }

                        }
                        break;
                    default:
                        break;
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
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
                    // hiển thị thông tin user
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    // lấy thông tin bài hát cần chỉnh sửa
                    SoundDTO sound = soundDAO.readSoundByIdSound(idSound);
                    String pathImage = sound.getPathImage();
                    String urlImage = "/assets/img/default/sound_default.png";
                    // hiển thị ảnh mặc định
                    if (pathImage != null) {
                        urlImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }
                    modelAndView.addObject("sound", sound);
                    modelAndView.addObject("urlImage", urlImage);
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
    public ModelAndView postEditorSound(
            @RequestParam(value = "avatar", required = false) MultipartFile fileImage,
            @RequestParam(value = "audio", required = false) MultipartFile fileAudio,
            @RequestParam(value = "isUpdateAudio", required = false) Boolean check,
            @ModelAttribute("sound") SoundDTO sound,
            HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/sound/editor";
        String urlRedirectRootSound = "redirect:/admin/sound";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    String idSound = sound.getIdSound();
                    String prefixFileName = idSound;

                    // cập nhật lại ảnh của sound
                    Long sizeFileImage = fileImage.getSize();
                    if (sizeFileImage != 0) {
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage,
                                prefixFileName);

                        saveFileUpload.setFullFileName();
                        saveFileUpload.commit();
                        String fullFileNameImage = saveFileUpload.getFullFileName();
                        soundDAO.updatePathImageByIdSound(idSound, fullFileNameImage);
                    }

                    // nếu thay đổi file audio thì cập nhật
                    Long sizeFileAudio = fileAudio.getSize();
                    if (sizeFileAudio != 0) {

                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_AUDIO, fileAudio,
                                prefixFileName);
                        saveFileUpload.setFullFileName();
                        saveFileUpload.commit();
                        String fullFileNameAudio = saveFileUpload.getFullFileName();
                        soundDAO.updatePathAudioByIdSound(idSound, fullFileNameAudio);
                    }

                    modelAndView.setViewName(urlRedirectRootSound);
                } catch (Exception ex) {

                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "sound/delete/{id}", method = RequestMethod.POST)
    public ModelAndView postDeleteSound(@PathVariable("id") String idSound, HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirect = "redirect:/admin/sound";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    // xóa 1 bài hát trong cơ sở dữ liệu
                    SoundDTO sound = soundDAO.readSoundByIdSound(idSound);
                    soundDAO.deleteSoundByIdSound(idSound);
                    String pathImage = sound.getPathImage();
                    String pathAudio = sound.getPathAudio();

                    // xóa file image trong thưc mục /asssets/img/data/
                    DeleteFile deleteFile = new DeleteFile(Constant.PATH_STATIC_SAVE_IMG, pathImage);
                    deleteFile.commit();

                    // xóa file audio trong thư mục /assets/audio/data/
                    deleteFile = new DeleteFile(Constant.PATH_STATIC_SAVE_AUDIO, pathAudio);
                    deleteFile.commit();

                    modelAndView.addObject("message", "delete success");
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    modelAndView.addObject("message", message);
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
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

                try {
                    // hiển thị thông tin user
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);

                    // hiển thị tất cả các user có trong database
                    List<UserDTO> users = new ArrayList<>();
                    users = userDAO.readAllUser();
                    modelAndView.addObject("users", users);

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
    public ModelAndView getAddUser(
            HttpSession session) {
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

                String pathImage = user.getPathImg();
                String urlImage = null;
                System.out.println(pathImage);
                if (pathImage != null) {
                    urlImage = Constant.URL_STATIC_IMAGE + pathImage;
                }

                modelAndView.addObject("urlImage", urlImage);

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
    public ModelAndView postAddUser(
            @RequestParam(value = "avatar") MultipartFile fileImage,
            @ModelAttribute("user") User user,
            HttpServletRequest request) {

        String fileView = "/page/admin/user/add";
        String urlRedirectRootUser = "redirect:/admin/user";
        String valueActionButton = request.getParameter("button");

        ModelAndView modelAndView = new ModelAndView(urlRedirectRootUser);

        switch (valueActionButton) {
            case "cancel":
                break;
            case "add":
                try {
                    // vừa lưu thông tin user vừa lấy
                    String idUser = userDAO.getIdUserWhileCreateUser(user);
                    String prefixNameFile = idUser;

                    // cập nhật ảnh nếu có ảnh
                    Long sizeImage = fileImage.getSize();
                    if (sizeImage != 0) {
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixNameFile);
                        saveFileUpload.setFullFileName();
                        saveFileUpload.commit();
                        String pathImage = saveFileUpload.getFullFileName();
                        // cap nhat thong path_image cho user
                        userDAO.updatePathImageByIdUser(idUser, pathImage);
                    }

                } catch (Exception ex) {
                    String message = "user name and email has been used";
                    modelAndView.setViewName(fileView);
                    modelAndView.addObject("message", message);
                }
                break;
            default:
                break;
        }

        return modelAndView;
    }

    @RequestMapping(value = "user/editor/{id}", method = RequestMethod.GET)
    public ModelAndView getEditorUser(
            @PathVariable("id") String idUser,
            HttpSession session) {

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

                    String pathImage = user.getPathImage();
                    String urlImage = "";
                    if (pathImage != null) {
                        urlImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }
                    modelAndView.addObject("urlImage", urlImage);

                    modelAndView.addObject("user", user);
                } catch (Exception ex) {
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
    public ModelAndView postEditorUser(@RequestParam(value = "avatar") MultipartFile fileImage,
            @ModelAttribute("user") UserDTO user, HttpServletRequest request) {
        String urlRedirectRootUser = "redirect:/admin/user";
        String fileView = "/page/admin/user/editor";
        String valueActionButton = request.getParameter("button");
        ModelAndView modelAndView = new ModelAndView(urlRedirectRootUser);
        switch (valueActionButton) {
            case "cancel":
                break;
            case "update":
                try {

                    String idUser = user.getIdUser();
                    Long sizeFile = fileImage.getSize();

                    if (sizeFile != 0) {
                        String prefixFileName = idUser;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage, prefixFileName);
                        saveFileUpload.setFullFileName();
                        saveFileUpload.commit();
                        String pathImage = saveFileUpload.getFullFileName();
                        userDAO.updatePathImageByIdUser(idUser, pathImage);
                    }

                    userDAO.updateUser(user);
                } catch (Exception ex) {
                    String message = "user name and email has been used";
                    modelAndView = new ModelAndView(fileView);
                    modelAndView.addObject("message", message);
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
            UserDTO user = userDAO.readUserByIdUser(idUser);
            userDAO.deleteUserByIdUser(idUser);
            String pathImage = user.getPathImage();
            DeleteFile deleteFile = new DeleteFile(Constant.PATH_STATIC_SAVE_IMG, pathImage);
            deleteFile.commit();
        } catch (Exception ex) {
            String message = ex.getMessage();
            modelAndView.addObject("message", message);
        }
        return modelAndView;
    }
}
