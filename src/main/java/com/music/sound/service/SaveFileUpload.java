package com.music.sound.service;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class SaveFileUpload {

    // filePath: là đường dẫn mặc định để lưu trong backend
    // vi dụ: example.jpg -> example là prefixFileName; .jpg là suffixFileName
    //
    private String filePath;
    private MultipartFile multipartFile;
    private String suffixFileName;
    private String prefixFileName;
    private String fullFileName;

    public SaveFileUpload(String filePath, MultipartFile multipartFile) {
        this.filePath = filePath;
        this.multipartFile = multipartFile;
        String fileName = multipartFile.getOriginalFilename();
        SeperateFileName(fileName);
    }

    public SaveFileUpload(String filePath, MultipartFile multipartFile, String newPrefixFileName) {
        this.filePath = filePath;
        this.multipartFile = multipartFile;
        String nameFile = multipartFile.getOriginalFilename();
        SeperateFileName(nameFile);
        this.prefixFileName = newPrefixFileName;
    }

    public SaveFileUpload(String filePath, MultipartFile multipartFile, String newPrefixFileName,
            String newSuffixFileName) {
        this.filePath = filePath;
        this.multipartFile = multipartFile;
        String nameFile = multipartFile.getOriginalFilename();
        SeperateFileName(nameFile);
        this.prefixFileName = newPrefixFileName;
        this.suffixFileName = newSuffixFileName;
    }

    public void setFullFileName() {
        this.fullFileName = this.prefixFileName + this.suffixFileName;
    }

    public String getFullFileName() {
        return this.fullFileName;
    }

    // dùng để tách tên file và đuôi: chẳng hạn như example.jpg -> example, jpg
    public void SeperateFileName(String fileName) {

        String prefixNameFile = fileName.substring(0, fileName.lastIndexOf(".") - 1);
        String suffixNameFile = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        this.prefixFileName = prefixNameFile;
        this.suffixFileName = suffixNameFile;
    }

    public void commit() throws Exception {
        if (filePath == null || prefixFileName == null || suffixFileName == null) {
            throw new Exception();
        }

        try {
            String fullFileName = this.filePath + this.prefixFileName + this.suffixFileName;
            File file = new File(fullFileName);
            multipartFile.transferTo(file);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
