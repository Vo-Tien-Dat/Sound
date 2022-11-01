package com.music.sound.DTO.SoundDTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class SoundDTO {
    private String id;
    private String nameSound;
    private String pathImage;
    private String nameUser;
    private Long typeSound;
}
