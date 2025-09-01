package com.example.GoCafe.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String save(MultipartFile file, String subDir);
    void delete(String url);
}