package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DTO.AlbumDTO.AlbumDTO;
import com.music.sound.DTO.SoundDTO.SoundDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.ArrayList;

@Controller
public class UploadController {

    @RequestMapping(value = "/upload/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/upload";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    // tính năng: bắt đầu upload file
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload() {
        String path = "/page/upload/start_upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    // tính năng: upload lại form thay đổi tên bài hát
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView postUpload(
            @RequestParam("file") List<MultipartFile> files,
            @ModelAttribute("album") AlbumDTO albumDTO) {
        String path = "/page/upload/pending_upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        List<SoundDTO> sounds = new ArrayList<SoundDTO>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                String nameSound = file.getOriginalFilename();
                SoundDTO soundDTO = new SoundDTO(nameSound);
                sounds.add(soundDTO);
            }

            AlbumDTO album = new AlbumDTO();
            album.setSounds(sounds);
            modelAndView.addObject("album", album);
        }
        if (albumDTO != null) {
            System.out.println(albumDTO);
        }

        return modelAndView;
    }
}
