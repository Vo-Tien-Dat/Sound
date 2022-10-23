package com.music.sound.DTO.SoundDTO;

import org.springframework.stereotype.Component;
import com.music.sound.model.Sound;

@Component
public class ConvertSound {
    public SoundDTORead convertEntityToDTO(Sound sound) {
        SoundDTORead soundDTORead = new SoundDTORead();

        String nameSound = sound.getNameSound();
        String nameUser = sound.getUser().getNameUser();
        String pathImage = sound.getPathImage();
        String pathAudio = sound.getPathAudio();

        soundDTORead.setNameSound(nameSound);
        soundDTORead.setNameUser(nameUser);
        soundDTORead.setPathImage(pathImage);

        return soundDTORead;
    }
}
