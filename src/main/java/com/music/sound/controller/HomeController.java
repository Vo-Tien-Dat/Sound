package com.music.sound.controller;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.music.sound.DAO.AlbumDAO;
import com.music.sound.DAO.AlbumDTO;
import com.music.sound.DAO.PlaylistDAO;
import com.music.sound.DAO.PlaylistDTO;
import com.music.sound.DAO.SoundDAO;
import com.music.sound.DAO.SoundDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DAO.UserDTO;
import com.music.sound.config.Constant;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private AlbumDAO albumDAO;

    @Autowired
    private SoundDAO soundDAO;

    @Autowired
    private PlaylistDAO playlistDAO;

    @Autowired
    private UserDAO userDAO;

    // @RequestMapping(value = "/home/*", method = RequestMethod.GET)
    // public ModelAndView getIndex() {
    // String pathRedirect = "redirect:/home";
    // ModelAndView modelAndView = new ModelAndView(pathRedirect);
    // return modelAndView;
    // }

    // @RequestMapping(value = "/home", method = RequestMethod.GET)
    // public ModelAndView getHome() {
    // String pathFile = "/page/home/index";
    // ModelAndView modelAndView = new ModelAndView(pathFile);

    // try {
    // List<AlbumDTORead> albumDTOReads = albumService.getAllAlbum();
    // for (AlbumDTORead albumDTORead : albumDTOReads) {
    // System.out.println(albumDTORead.getNameAlbum());
    // System.out.println(albumDTORead.getPathUrl());
    // }
    // } catch (Exception ex) {
    // System.out.println(ex.getMessage());
    // }

    // // hiện thị 10 bài hát
    // List<SoundDTO> sounds = new ArrayList<>();
    // // sounds.add(new SoundDTO("hello", "Hồi duyên", "", "Khởi Vũ",
    // // Long.valueOf(1)));
    // // sounds.add(new SoundDTO("test", "Ngưởi có còn thương", "", "Dee Trần",
    // // Long.valueOf(0)));

    // // hiện thị gợi ý 10 người chưa được theo dõi
    // List<UserDTOHome> users = new ArrayList<>();
    // users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
    // users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
    // modelAndView.addObject("sounds", sounds);
    // modelAndView.addObject("users", users);

    // // hiển thị 10 albums
    // List<AlbumDTORead> albums = new ArrayList<>();
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello",
    // "hello"));
    // modelAndView.addObject("albums", albums);

    // // hiện thị 10 playlist bất kì
    // List<PlaylistDTORead> playlists = new ArrayList<>();
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu",
    // "hello", "hello"));
    // modelAndView.addObject("playlists", playlists);
    // return modelAndView;
    // }

    // feature: show home page
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome() {
        String pathFile = "/page/home/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);

        List<AlbumDTO> albums = new ArrayList<>();
        List<PlaylistDTO> playlists = new ArrayList<>();
        List<SoundDTO> sounds = new ArrayList<>();

        try {
            albums = albumDAO.readAllAlbumHaveLimit(Constant.LIMIT_ALBUM_HOME);
            playlists = playlistDAO.readAllPLaylistHaveLimit(Constant.LIMIT_PLAYLIST_HOME);
            sounds = soundDAO.readAllSoundHaveLimit(Constant.LIMIT_SOUND_HOME);

            modelAndView.addObject("albums", albums);
            modelAndView.addObject("playlists", playlists);
            modelAndView.addObject("sounds", sounds);
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return modelAndView;
    }

    // feature: save album in your favorite
    @RequestMapping(value = "/favorite/create/album/{id}", method = RequestMethod.GET)
    public ModelAndView postFavoriteAlbum(@RequestParam("id") String idAlbum) {
        String fileView = "/page/home/index1";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: save playlist in your favorite
    @RequestMapping(value = "/favorite/create/playlist/{id}", method = RequestMethod.POST)
    public ModelAndView postFavoritePlaylist(@RequestParam("id") String idPlaylist) {
        String fileView = "/page/home/index1";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: save sound in your favorite
    @RequestMapping(value = "/favorite/create/sound/{id}", method = RequestMethod.GET)
    public ModelAndView postFavoriteSound(@RequestParam("id") String idSound) {
        String fileView = "/page/home/index1";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: redirect to home
    @RequestMapping(value = "/album/*", method = RequestMethod.GET)
    public ModelAndView getRootAlbum() {
        String urlRedirect = "redirect:/album";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/album", method = RequestMethod.GET)
    public ModelAndView getAlbum() {
        String fileView = "/page/album/index";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: show item album
    @RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
    public ModelAndView getAlbum(@PathVariable("id") String idAlbum) {
        String fileView = "/page/album/index";
        String urlRediect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(fileView);

        try {

            AlbumDTO album = albumDAO.readAlbumByIdAlbum(idAlbum);
            List<SoundDTO> sounds = soundDAO.readAllSoundByIdAlbum(idAlbum);
            if (album == null) {
                modelAndView.setViewName(urlRediect);
            } else {
                modelAndView.addObject("album", album);
                modelAndView.addObject("sounds", sounds);
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRediect);
        }

        return modelAndView;
    }

    // feature: redirect to home
    @RequestMapping(value = "/playlist/*", method = RequestMethod.GET)
    public ModelAndView getRootPlaylist() {
        String urlRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(urlRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    public ModelAndView getPlaylist() {
        String fileView = "/page/playlist/index1";
        ModelAndView modelAndView = new ModelAndView(fileView);
        return modelAndView;
    }

    // feature: show item playlist
    @RequestMapping(value = "/playlist/{id}", method = RequestMethod.GET)
    public ModelAndView getPlaylist(@PathVariable("id") String idPlaylist) {
        String fileView = "/page/playlist/index1";
        String urlRedirect = "redirect:/home";
        ModelAndView modelAndView = new ModelAndView(fileView);
        try {
            PlaylistDTO playlist = playlistDAO.readPlaylistByIdPlaylist(idPlaylist);
            if (playlist == null) {
                modelAndView.setViewName(urlRedirect);
            } else {
                modelAndView.addObject("playlist", playlist);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            modelAndView.setViewName(urlRedirect);
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

}
