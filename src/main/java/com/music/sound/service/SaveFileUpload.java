package com.music.sound.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.io.File;

@Data
public class SaveFileUpload {

    private String pathFile;
    private MultipartFile multipartFile;
    private String nameFileOld;
    private String nameFileNew;

    public SaveFileUpload(String pathFile, String nameFileOld, String nameFileNew, MultipartFile multipartFile) {
        this.pathFile = pathFile;
        this.nameFileOld = nameFileOld;
        this.multipartFile = multipartFile;
        this.nameFileNew = nameFileNew;
    }

    public void save() throws Exception {
        if (pathFile == null || nameFileOld == null || multipartFile == null) {
            String message = "the field is null";
            throw new Exception(message);
        }

        String suffixNameFile = this.nameFileOld.substring(this.nameFileOld.lastIndexOf("."),
                this.nameFileOld.length());

        String nameFileNew = this.nameFileNew + suffixNameFile;

        File file = new File(pathFile + nameFileNew);
        multipartFile.transferTo(file);
    }

}
