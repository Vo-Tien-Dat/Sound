package com.music.sound.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.music.sound.config.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.File;

@Service
@AllArgsConstructor
@Data
public class UploadService {

    private String nameFile;
    private String pathFile;
    private MultipartFile multipartFile;

    public UploadService() {
        this.nameFile = null;
        this.pathFile = Constant.PATH_STATIC_SAVE_AUDIO;
        this.multipartFile = null;
    }

    public void save() throws Exception {

        if (nameFile == null || pathFile == null || multipartFile == null) {
            String message = "field is null";
            throw new Exception(message);
        }
        File file = new File(pathFile + nameFile);
        multipartFile.transferTo(file);
    }
}
