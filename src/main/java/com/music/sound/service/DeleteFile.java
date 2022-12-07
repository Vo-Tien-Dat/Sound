package com.music.sound.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DeleteFile {
    private String filePath;
    private String prefixFileName;
    private String suffixFileName;
    private String fullFileName;

    public DeleteFile() {

    }

    public DeleteFile(String filePath, String fullFileName) {
        this.filePath = filePath;
        this.fullFileName = fullFileName;
    }

    public DeleteFile(String filePath, String prefixfileName, String suffixFileName) {
        this.filePath = filePath;
        this.prefixFileName = prefixfileName;
        this.suffixFileName = suffixFileName;
    }

    public void commit() throws Exception {

        String fullFileName = null;
        if (this.fullFileName != null) {
            fullFileName = this.filePath + this.fullFileName;
        } else {
            fullFileName = this.filePath + this.prefixFileName + this.suffixFileName;
        }

        try {
            File file = new File(fullFileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
