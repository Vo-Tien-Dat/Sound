package com.music.sound.service;

import java.util.List;
import java.util.ArrayList;

import com.music.sound.DAO.SoundDAO;
import com.music.sound.DTO.SoundDTO.ConvertSound;
import com.music.sound.DTO.SoundDTO.SoundDTORead;
import com.music.sound.model.Sound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoundService {
    @Autowired
    private ConvertSound convertSound;

    @Autowired
    private SoundDAO soundDAO;

    public List<SoundDTORead> getAllSound() {

        List<Sound> sounds = soundDAO.findAllSound();

        List<SoundDTORead> soundDTOReads = new ArrayList<>();

        for (Sound sound : sounds) {
            SoundDTORead soundDTORead = convertSound.convertEntityToDTO(sound);
            soundDTOReads.add(soundDTORead);
        }

        return soundDTOReads;
    }

    public void save(Sound sound) {
        try {
            soundDAO.insertSound(sound);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateSoundById(Sound sound) {
        soundDAO.updateSoundByIdSound(sound);
    }

    public String getIdSound(Sound sound) {
        String id = soundDAO.getIdSoundBeforeInsert(sound);
        return id;
    }
}
