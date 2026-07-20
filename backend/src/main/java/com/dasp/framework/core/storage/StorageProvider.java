package com.dasp.framework.core.storage;

import java.io.InputStream;

public interface StorageProvider {

    String uploadFile(String path, String fileName, InputStream inputStream, String contentType);

    InputStream downloadFile(String fileKey);

    void deleteFile(String fileKey);

    String getPublicUrl(String fileKey);
}
