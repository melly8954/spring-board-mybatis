package com.melly.spring_board_mybatis.filestorage.service;

import com.melly.spring_board_mybatis.filestorage.domain.StoredFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageStrategy {
    List<StoredFile> store(List<MultipartFile> files, String typeKey);
    StoredFile store(MultipartFile file, String typeKey);
    String generateFileUrl(StoredFile file, String typeKey);
    Resource load(String path);
}
