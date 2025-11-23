package com.melly.spring_board_mybatis.filestorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<String> saveFiles(List<MultipartFile> files, String typeKey);
    String saveFile(MultipartFile file, String typeKey);
    void deleteFile(String filePath, String typeKey);
}
