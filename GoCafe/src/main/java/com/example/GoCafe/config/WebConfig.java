package com.example.GoCafe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.root:uploads}")
    private String uploadRoot;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 예: /files/** → (프로젝트)/uploads/ 아래 파일 서빙
        String fileRoot = Paths.get(uploadRoot).toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/files/**")
                .addResourceLocations(fileRoot);
    }
}
