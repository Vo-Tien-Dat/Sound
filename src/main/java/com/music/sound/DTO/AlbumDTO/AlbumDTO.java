package com.music.sound.DTO.AlbumDTO;

import lombok.Data;
import com.music.sound.DTO.SoundDTO.SoundDTO;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
public class AlbumDTO {
    private String nameAlbum;
    private List<SoundDTO> sounds;
}
