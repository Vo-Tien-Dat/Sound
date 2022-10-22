package com.music.sound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.service.UserService;
import java.util.List;
import java.util.ArrayList;

import com.music.sound.DTO.AlbumDTO.AlbumDTORead;
import com.music.sound.DTO.PlaylistDTO.PlaylistDTORead;
import com.music.sound.DTO.SoundDTO.SoundDTO;
import com.music.sound.DTO.UserDTO.UserDTOHome;

@Controller
public class HomeController {

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
        // hiện thị 10 bài hát
        List<SoundDTO> sounds = new ArrayList<>();
        sounds.add(new SoundDTO("Hồi duyên", "", "Khởi Vũ"));
        sounds.add(new SoundDTO("Ngưởi có còn thương", "", "Dee Trần"));

        // hiện thị gợi ý 10 người chưa được theo dõi
        List<UserDTOHome> users = new ArrayList<>();
        users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
        users.add(new UserDTOHome("Seii LuiiBao", "", "18", "20", "20"));
        modelAndView.addObject("sounds", sounds);
        modelAndView.addObject("users", users);

        // hiển thị 10 albums
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

        // hiện thị 10 playlist bất kì
        List<PlaylistDTORead> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        playlists.add(new PlaylistDTORead("Nỗi Đau kéo dài", "Hồ quang Hiếu", "hello", "hello"));
        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }
}
