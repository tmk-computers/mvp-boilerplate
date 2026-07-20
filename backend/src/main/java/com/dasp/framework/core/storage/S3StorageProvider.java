package com.dasp.framework.core.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service("s3StorageProvider")
public class S3StorageProvider implements StorageProvider {

    @Override
    public String uploadFile(String path, String fileName, InputStream inputStream, String contentType) {
        log.info("Simulating AWS S3 file upload for file: {}/{}", path, fileName);
        return path + "/" + fileName;
    }

    @Override
    public InputStream downloadFile(String fileKey) {
        log.info("Simulating AWS S3 file download for key: {}", fileKey);
        return InputStream.nullInputStream();
    }

    @Override
    public void deleteFile(String fileKey) {
        log.info("Simulating AWS S3 file deletion for key: {}", fileKey);
    }

    @Override
    public String getPublicUrl(String fileKey) {
        return "https://dasp-bucket.s3.amazonaws.com/" + fileKey;
    }
}
