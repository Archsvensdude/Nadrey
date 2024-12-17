package com.web.mb.AttractionSearching.model;

import org.springframework.web.multipart.MultipartFile;

public class Attach {
    private MultipartFile file;
    private String fileName;

    // Getter와 Setter
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
