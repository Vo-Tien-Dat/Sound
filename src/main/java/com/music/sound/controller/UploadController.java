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
import org.springframework.http.MediaType;

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
        String path = "/page/upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    // tính năng: upload lại form thay đổi tên bài hát
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView postUpload(
            @RequestParam("file") List<MultipartFile> files,
            @RequestParam(name = "name-sound") String nameFile,
            @ModelAttribute("album") AlbumDTO albumDTO) {
        String path = "/page/upload/pending_upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        List<SoundDTO> sounds = new ArrayList<SoundDTO>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                String nameSound = file.getOriginalFilename();
                SoundDTO soundDTO = new SoundDTO();
                soundDTO.setNameSound(nameSound);
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
