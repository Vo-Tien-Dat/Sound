package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.ArrayList;

import com.music.sound.DTO.AlbumDTO.AlbumDTORead;
import com.music.sound.DTO.PlaylistDTO.PlaylistDTORead;
import com.music.sound.DTO.SoundDTO.SoundDTORead;

@Controller
public class LibraryController {
    @RequestMapping(value = "/library/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/library/overview";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public ModelAndView getLibrary() {
        String pathRedirect = "redirect:/library/overview";

        ModelAndView modelAndView = new ModelAndView(pathRedirect);

        return modelAndView;
    }

    @RequestMapping(value = "/library/overview", method = RequestMethod.GET)
    public ModelAndView getOverview() {

        String pathFile = "/page/library/overview/index";

        ModelAndView modelAndView = new ModelAndView(pathFile);

        return modelAndView;
    }

    @RequestMapping(value = "/library/albums", method = RequestMethod.GET)
    public ModelAndView getAlbum() {

        String pathFile = "/page/library/album/index";

        ModelAndView modelAndView = new ModelAndView(pathFile);

        List<AlbumDTORead> albums = new ArrayList<>();

        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        albums.add(new AlbumDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        modelAndView.addObject("albums", albums);
        return modelAndView;
    }

    @RequestMapping(value = "/library/playlists", method = RequestMethod.GET)
    public ModelAndView getPlaylist() {
        String pathFile = "/page/library/playlist/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        List<PlaylistDTORead> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));
        playlists.add(new PlaylistDTORead("hello", "hello", "hello", "hello"));
        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }

    @RequestMapping(value = "/library/sounds", method = RequestMethod.GET)
    public ModelAndView getTracks() {
        String pathFile = "/page/library/track/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        List<SoundDTORead> sounds = new ArrayList<>();
        sounds.add(new SoundDTORead("hello", "hello", "hello"));
        modelAndView.addObject("sounds", sounds);
        return modelAndView;
    }

    @RequestMapping(value = "/library/following", method = RequestMethod.GET)
    public ModelAndView getFollowing() {
        String pathFile = "/page/library/following/index";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("namePage", "Library");
        return modelAndView;
    }
}
