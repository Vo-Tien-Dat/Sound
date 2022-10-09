package com.music.sound.DTO.SoundDTO;

import com.music.sound.model.Sound;

import lombok.Data;

@Data
public class SoundDTO {
    private String nameSound;

    public SoundDTO() {
    }

    public SoundDTO(String nameSound) {
        this.nameSound = nameSound;
    }
}
