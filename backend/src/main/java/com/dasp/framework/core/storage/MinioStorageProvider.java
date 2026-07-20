package com.dasp.framework.core.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service("minioStorageProvider")
public class MinioStorageProvider implements StorageProvider {

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        log.info("Simulating MinIO file upload for file: {}/{}", path, fileName);
        return path + "/" + fileName;
    }

    @Override
    public InputStream downloadFile(String fileKey) {
        log.info("Simulating MinIO file download for key: {}", fileKey);
        return InputStream.nullInputStream();
    }

    @Override
    public void deleteFile(String fileKey) {
        log.info("Simulating MinIO file deletion for key: {}", fileKey);
    }

    @Override
    public String getPublicUrl(String fileKey) {
        return "http://minio-server:9000/dasp-bucket/" + fileKey;
    }
}
