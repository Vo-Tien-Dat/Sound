package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.TypeSoundDAO;
import com.music.sound.DTO.AlbumDTO.AlbumDTO;
import com.music.sound.DTO.SoundDTO.SoundDTO;
import com.music.sound.model.Sound;
import com.music.sound.model.TypeSound;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.music.sound.service.SoundService;
import com.music.sound.service.UploadService;

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SoundService soundService;

    @Autowired
    private TypeSoundDAO typeSoundDAO;

    @RequestMapping(value = "/upload/*", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        String pathRedirect = "redirect:/upload";
        ModelAndView modelAndView = new ModelAndView(pathRedirect);
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload() {
        String path = "/page/upload/start_upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    @RequestMapping(value = "/pending_upload", method = RequestMethod.POST, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView postPendingUpload(
            @RequestParam("file") List<MultipartFile> files,
            @ModelAttribute("album") AlbumDTO albumDTO) {

        String path = "/page/upload/pending_upload/index";

        ModelAndView modelAndView = new ModelAndView(path);

        List<SoundDTO> sounds = new ArrayList<SoundDTO>();

        List<TypeSound> typeSounds = typeSoundDAO.findAllTypeSound();

        int numberFile = Integer.valueOf(files.size());

        if (!files.isEmpty()) {
            for (int index = 0; index < numberFile; ++index) {

                Sound sound = new Sound();
                SoundDTO soundDTO = new SoundDTO();

                String nameSound = files.get(index).getOriginalFilename();

                try {
                    String id = soundService.getIdSound(sound);
                    uploadService.setNameFile(id);
                    uploadService.setMultipartFile(files.get(index));
                    uploadService.save();

                    soundDTO.setId(id);
                    soundDTO.setNameSound(nameSound);

                    sounds.add(soundDTO);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            AlbumDTO album = new AlbumDTO();
            album.setSounds(sounds);

            modelAndView.addObject("album", album);
            modelAndView.addObject("typesounds", typeSounds);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/end_upload", method = RequestMethod.POST, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView postPendingUpload(@ModelAttribute("album") AlbumDTO albumDTO) {
        String path = "/page/upload/end_upload/index";
        ModelAndView modelAndView = new ModelAndView(path);
        List<SoundDTO> sounds = albumDTO.getSounds();
        System.out.println(sounds);
        for (SoundDTO sound : sounds) {
            if (sound != null) {
                String id = sound.getId();
                String nameSound = sound.getNameSound();
                Long typeSound = sound.getTypeSound();
                System.out.println(typeSound);
                TypeSound typeSoundObject = new TypeSound();
                typeSoundObject.setIdTypeSound(typeSound);

                Sound soundUpdate = new Sound();
                soundUpdate.setId(UUID.fromString(id));
                soundUpdate.setNameSound(nameSound);
                soundUpdate.setTypeSound(typeSoundObject);

                soundService.updateSoundById(soundUpdate);
            }
        }
        modelAndView.addObject("sounds", sounds);
        return modelAndView;
    }
}
