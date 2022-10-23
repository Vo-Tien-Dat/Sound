package com.music.sound.service;

import java.util.List;
import java.util.ArrayList;

import com.music.sound.DAO.SoundDAO;
import com.music.sound.DTO.SoundDTO.ConvertSound;
import com.music.sound.DTO.SoundDTO.SoundDTORead;
import com.music.sound.model.Sound;

import org.springframework.beans.factory.annotation.Autowired;

public class SoundService {
    @Autowired
    private ConvertSound convertSound;

    @Autowired
    private SoundDAO soundDAO;

    public List<SoundDTORead> getAllSound() {
        List<SoundDTORead> soundDTOReads = new ArrayList<>();
        List<Sound> sounds = soundDAO.findAllSound();
        for (Sound sound : sounds) {
            SoundDTORead soundDTORead = convertSound.convertEntityToDTO(sound);
            soundDTOReads.add(soundDTORead);
        }

        return soundDTOReads;
    }
}
