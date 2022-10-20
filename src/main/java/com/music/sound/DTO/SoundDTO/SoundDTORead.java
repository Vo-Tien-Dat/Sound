package com.music.sound.DTO.SoundDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoundDTORead {
    private String nameSound;
    private String nameUser;
    private String pathImage;
}
