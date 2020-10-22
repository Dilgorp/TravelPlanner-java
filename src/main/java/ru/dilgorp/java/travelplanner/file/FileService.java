package ru.dilgorp.java.travelplanner.file;

import java.util.UUID;

public interface FileService {
    byte[] getBytes(String path);

    String createPath(UUID requestUUID, String fileName, String contentType, String folder);

    void saveFile(byte[] bytes, String path);
}
