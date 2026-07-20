package com.dasp.framework.core.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service("localStorageProvider")
public class LocalStorageProvider implements StorageProvider {

    @Value("${dasp.storage.local.base-path:./uploads}")
    private String basePath;

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        try {
            Path targetDir = Path.of(basePath, path);
            Files.createDirectories(targetDir);
            Path targetFile = targetDir.resolve(fileName);
            Files.copy(inputStream, targetFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            log.info("File uploaded locally to: {}", targetFile.toAbsolutePath());
            return path + "/" + fileName;
        } catch (IOException e) {
            log.error("Error uploading file locally", e);
            throw new RuntimeException("Failed to upload local file", e);
        }
    }

    @Override
    public InputStream downloadFile(String fileKey) {
        try {
            Path file = Path.of(basePath, fileKey);
            return Files.newInputStream(file);
        } catch (IOException e) {
            log.error("Error downloading file locally", e);
            throw new RuntimeException("Failed to download local file", e);
        }
    }

    @Override
    public void deleteFile(String fileKey) {
        try {
            Path file = Path.of(basePath, fileKey);
            Files.deleteIfExists(file);
            log.info("File deleted locally: {}", fileKey);
        } catch (IOException e) {
            log.error("Error deleting local file", e);
        }
    }

    @Override
    public String getPublicUrl(String fileKey) {
        return "/api/v1/files/download/" + fileKey;
    }
}
