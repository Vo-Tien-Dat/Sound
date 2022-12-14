package com.music.sound.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.music.sound.model.User;
import com.music.sound.service.DeleteFile;
import com.music.sound.service.SaveFileUpload;

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
                    } else {
                        for (AlbumDTO album : albums) {
                            String pathImage = album.getPathImage();
                            String defaultPathImage = "/assets/img/default/sound_default.png";
                            String newUrlPathImage = defaultPathImage;
                            if (pathImage != null) {
                                newUrlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                            }
                            album.setPathImage(newUrlPathImage);
                        }
                    }

                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

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

    // xử lí việc thêm album
    // xử lí việc lưu tên bài hát và ca sĩ vào cơ sở dữ liệu và lấy ra được image
    @RequestMapping(value = "album/editor", method = RequestMethod.POST)
    public ModelAndView postEditorAlbum(
            @RequestParam(value = "album_name", required = true) String nameAlbum,
            @RequestParam(value = "singer_name", required = true) String nameSinger,
            HttpSession session) {

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

                    // lưu thay đổi tên album vs tên ca sĩ vào cở sở dữ liệu
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
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

    @RequestMapping(value = "album/editor/{id}", method = RequestMethod.GET)
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

                    // hiển thị người dùng ở sidebar
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    // hiển thị thông tin của album
                    album = albumDAO.readAlbumByIdAlbum(idAlbum);
                    modelAndView.addObject("album", album);

                    // những bài hat được thêm vào album
                    soundAddedAlbums = soundDAO.readAllSoundByIdAlbum(idAlbum);
                    for (SoundDTO sound : soundAddedAlbums) {
                        String pathImage = sound.getPathImage();
                        String newUrlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        if (pathImage != null) {
                            newUrlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        sound.setPathImage(newUrlPathImage);
                    }
                    modelAndView.addObject("soundAddedAlbums", soundAddedAlbums);

                    // hiển thị tất các bài hát chưa thuộc album nào
                    sounds = soundDAO.readAllSoundByIdAlbumIsNull();
                    for (SoundDTO sound : sounds) {
                        String pathImage = sound.getPathImage();
                        String newUrlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        if (pathImage != null) {
                            newUrlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        sound.setPathImage(newUrlPathImage);
                    }
                    modelAndView.addObject("sounds", sounds);

                    // hiển thị ảnh đại diện của album
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

    // xử lí cập nhật thông tin album
    @RequestMapping(value = "album/update/infor", method = RequestMethod.POST)
    public ModelAndView postUpdateInforAlbum(
            @RequestParam(value = "id_album", required = true) String idAlbum,
            @RequestParam(value = "album_name", required = true) String nameAlbum,
            @RequestParam(value = "singer_name", required = true) String nameSinger,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAlbumEditor = "redirect:/admin/album/editor/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);
                    album.setNameAlbum(nameAlbum);
                    album.setNameSinger(nameSinger);
                    albumDAO.updateAlbum2Arg(album);

                    // chuyển hướng về trang editor
                    urlRedirectAlbumEditor = urlRedirectAlbumEditor + idAlbum;
                    modelAndView.setViewName(urlRedirectAlbumEditor);

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

    // xử lí việc upload ảnh đại diện
    @RequestMapping(value = "album/update/image", method = RequestMethod.POST)
    public ModelAndView postUploadImageAlbum(
            @RequestParam("id_album") String idAlbum,
            @RequestParam("avatar") MultipartFile fileImage,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAlbumEditor = "redirect:/admin/album/editor/";

        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {

                    AlbumDTO oldAlbum = albumDAO.readAlbumByIdAlbum(idAlbum);
                    AlbumDTO album = new AlbumDTO();
                    album.setIdAlbum(idAlbum);

                    Long fileSize = fileImage.getSize();
                    String oldPathImage = oldAlbum.getPathImage();

                    // lưu file ảnh vào
                    if (fileSize != 0) {
                        String prefixFileName = idAlbum;
                        saveFileUpload = new SaveFileUpload(Constant.PATH_STATIC_SAVE_IMG, fileImage,
                                prefixFileName);
                        saveFileUpload.setFullFileName();
                        String fullFileName = saveFileUpload.getFullFileName();
                        album.setPathImage(fullFileName);
                        saveFileUpload.commit();
                    }

                    if (oldPathImage != null) {
                        album.setPathImage(oldPathImage);
                    }

                    albumDAO.updateImageAlbumByIdAlbum(album);

                    // chuyển hướng về trang editor
                    urlRedirectAlbumEditor = urlRedirectAlbumEditor + idAlbum;
                    modelAndView.setViewName(urlRedirectAlbumEditor);

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

    @RequestMapping(value = "album/update/add/sound", method = RequestMethod.POST)
    public ModelAndView postAddSoundIntoAlbum(
            @RequestParam(value = "id_album", required = true) String idAlbum,
            @RequestParam(value = "id_sound", required = true) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectEditorAlbum = "redirect:/admin/album/editor/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    soundDAO.updateIdAlbumByIdSound(idAlbum, idSound);
                    urlRedirectEditorAlbum = urlRedirectEditorAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectEditorAlbum);

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

    @RequestMapping(value = "album/update/delete/sound", method = RequestMethod.POST)
    public ModelAndView postDeleteSoundIntoAlbum(
            @RequestParam(value = "id_album", required = true) String idAlbum,
            @RequestParam(value = "id_sound", required = true) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectEditorAlbum = "redirect:/admin/album/editor/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    soundDAO.updateIdAlbumByIdSound(null, idSound);
                    urlRedirectEditorAlbum = urlRedirectEditorAlbum + idAlbum;
                    modelAndView.setViewName(urlRedirectEditorAlbum);

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

    // kết thúc phần xử lí album

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

                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    List<PlaylistDTO> playlists = new ArrayList<>();
                    playlists = playlistDAO.readAllPLaylist();
                    if (playlists.size() == 0) {
                        playlists = null;
                    }
                    String urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                    for (PlaylistDTO playlist : playlists) {
                        String pathImage = playlist.getPathImage();
                        if (pathImage != null) {
                            urlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        } else {
                            urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        }
                        playlist.setPathImage(urlPathImage);
                    }
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

    @RequestMapping(value = "playlist/editor", method = RequestMethod.POST)
    public ModelAndView postEditorPlaylist(
            @RequestParam(value = "playlist_name", required = true) String namePlaylist, HttpSession session) {

        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String fileView = "/page/admin/playlist/add";
        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlEditorPlaylist = "redirect:/admin/playlist/editor/";

        ModelAndView modelAndView = new ModelAndView(fileView);
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    String idPlaylist = playlistDAO.getIdPlaylistBeforeCreatePlaylist();

                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);
                    playlist.setNamePlaylist(namePlaylist);

                    playlistDAO.updatePlaylist(playlist);

                    urlEditorPlaylist = urlEditorPlaylist + idPlaylist;
                    modelAndView.setViewName(urlEditorPlaylist);

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

    // hiển thị trang thêm playlist
    @RequestMapping(value = "playlist/editor/{id}", method = RequestMethod.GET)
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
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    modelAndView.addObject("playlist", playlist);

                    // cac bai hat chua duoc them vao
                    sounds = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylistFirst(idPlaylist);
                    for (SoundDTO sound : sounds) {
                        String urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        String pathImage = sound.getPathImage();
                        if (pathImage != null) {
                            urlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        sound.setPathImage(urlPathImage);
                    }
                    modelAndView.addObject("sounds", sounds);
                    // cac bai hat da duoc them vao
                    soundAddedPlaylists = playlistDAO.readAllSoundByIdPlaylistFromSoundPlaylist(idPlaylist);
                    for (SoundDTO sound : soundAddedPlaylists) {
                        String urlPathImage = Constant.DEFAULT_SOUND_IMAGE;
                        System.out.println(sound);
                        String pathImage = sound.getPathImage();
                        if (pathImage != null) {
                            urlPathImage = Constant.URL_STATIC_IMAGE + pathImage;
                        }
                        sound.setPathImage(urlPathImage);
                    }
                    modelAndView.addObject("soundAddedPlaylists", soundAddedPlaylists);

                    String pathImage = playlist.getPathImage();
                    String urlAvatarImage = null;
                    if (pathImage != null) {
                        urlAvatarImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }
                    modelAndView.addObject("urlAvatarImage", urlAvatarImage);
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

    @RequestMapping(value = "playlist/update/infor", method = RequestMethod.POST)
    public ModelAndView postUpdateInforPlaylist(
            @RequestParam(value = "id_playlist", required = true) String idPlaylist,
            @RequestParam(value = "playlist_name", required = true) String namePlaylist,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlEditorPlaylist = "redirect:/admin/playlist/editor/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);
                    playlist.setNamePlaylist(namePlaylist);

                    playlistDAO.updateNamePlaylistByIdPlaylist(playlist);

                    urlEditorPlaylist = urlEditorPlaylist + idPlaylist;
                    modelAndView.setViewName(urlEditorPlaylist);

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

    @RequestMapping(value = "playlist/update/image", method = RequestMethod.POST)
    public ModelAndView postUpdateImagePlaylist(
            @RequestParam(value = "id_playlist", required = true) String idPlaylist,
            @RequestParam(value = "avatar", required = true) MultipartFile fileImage,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlEditorPlaylist = "redirect:/admin/playlist/editor/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    PlaylistDTO oldPlaylist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setIdPlaylist(idPlaylist);

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

                    playlistDAO.updateImagePlaylistByIdPlaylist(playlist);
                    urlEditorPlaylist = urlEditorPlaylist + idPlaylist;
                    modelAndView.setViewName(urlEditorPlaylist);

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

    @RequestMapping(value = "playlist/update/add/sound", method = RequestMethod.POST)
    public ModelAndView postAddSoundIntoPlaylist(
            @RequestParam(value = "id_playlist", required = true) String idPlaylist,
            @RequestParam(value = "id_sound", required = true) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlEditorPlaylist = "redirect:/admin/playlist/editor/";
        ModelAndView modelAndView = new ModelAndView();

        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    playlistDAO.createSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
                    urlEditorPlaylist = urlEditorPlaylist + idPlaylist;
                    modelAndView.setViewName(urlEditorPlaylist);
                } catch (Exception ex) {
                    String message = "không thể thêm bài hát";
                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }
        return modelAndView;

    }

    @RequestMapping(value = "playlist/update/delete/sound", method = RequestMethod.POST)
    public ModelAndView postDeleteSoundFromPlaylist(
            @RequestParam(value = "id_playlist", required = true) String idPlaylist,
            @RequestParam(value = "id_sound", required = true) String idSound,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlEditorPlaylist = "redirect:/admin/playlist/editor/";
        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleAdmin = roleDTO.getRoleUser().compareTo(Constant.ROLE_ADMIN) == 0 ? true : false;
            if (isRoleAdmin) {
                try {
                    playlistDAO.deleteSoundPlaylistByIdSoundAndIdPlaylist(idSound, idPlaylist);
                    urlEditorPlaylist = urlEditorPlaylist + idPlaylist;
                    modelAndView.setViewName(urlEditorPlaylist);
                } catch (Exception ex) {
                    String message = "không thể xóa bài hát";

                }
            } else {
                modelAndView.setViewName(urlRedirectHome);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }
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
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

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
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("path_image_user", urlPathImageUser);
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
                try {
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
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    // lấy danh sach thể loại bài hát
                    List<TypeSoundDTO> typeSounds = new ArrayList<>();
                    typeSounds = typeSoundDAO.readAllTypeSound();
                    modelAndView.addObject("typeSounds", typeSounds);

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

                    soundDAO.updateNameSoundAndNameSingerByIdSound(sound);

                    modelAndView.setViewName(urlRedirectRootSound);
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
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("path_image_user", urlPathImageUser);
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
                try {
                    String idUser = roleDTO.getIdUser();
                    User user = new User();
                    UserDTO userDTO = userDAO.readUserByIdUser(idUser);
                    String nameUser = userDTO.getNameUser();
                    String pathImagerUser = userDTO.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImagerUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImagerUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    String pathImage = user.getPathImg();
                    String urlImage = null;
                    if (pathImage != null) {
                        urlImage = Constant.URL_STATIC_IMAGE + pathImage;
                    }

                    modelAndView.addObject("urlImage", urlImage);

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
                    // hiển thị thông tin của user đã đăng nhập
                    String idUserRoot = roleDTO.getIdUser();
                    UserDTO userDTO = userDAO.readUserByIdUser(idUserRoot);
                    String nameUser = userDTO.getNameUser();
                    String pathImagerUser = userDTO.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImagerUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImagerUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    // hiển thị thông tin user được phép chỉnh sửa
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
