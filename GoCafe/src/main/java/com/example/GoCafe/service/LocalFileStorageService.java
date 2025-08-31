package com.example.GoCafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {

    // 업로드 루트 디렉터리 (기본값: 프로젝트 루트의 uploads)
    @Value("${app.upload.root:uploads}")
    private String uploadRoot;

    @Override
    public String save(MultipartFile file, String subDir) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("file must not be empty");
        }
        try {
            String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) ext = original.substring(dot);
            String filename = UUID.randomUUID() + ext;

            Path dir = Paths.get(uploadRoot, subDir).toAbsolutePath().normalize();
            Files.createDirectories(dir);

            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // 브라우저에서 접근할 URL 형태로 반환 (/files/** 로 매핑 예정)
            String normalizedSub = subDir.replace("\\", "/");
            return "/files/" + normalizedSub + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void delete(String url) {
        // url 예: /files/reviews/123/xxx.jpg  → 실제 경로로 환산
        if (url == null || !url.startsWith("/files/")) return;
        String rel = url.substring("/files/".length()); // reviews/123/xxx.jpg
        Path path = Paths.get(uploadRoot, rel).toAbsolutePath().normalize();
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {}
    }
}
