package com.web.mb.AttractionSearching.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        // 업로드 디렉토리 생성
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 파일 저장
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        return filePath;
    }
}