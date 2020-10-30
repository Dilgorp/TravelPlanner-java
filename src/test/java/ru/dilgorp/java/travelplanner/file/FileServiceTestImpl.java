package ru.dilgorp.java.travelplanner.file;

import java.util.UUID;

public class FileServiceTestImpl implements FileService {

    private final byte[] bytes = new byte[0];

    @Override
    public byte[] getBytes(String path) {
        return bytes;
    }

    @Override
    public String createPath(UUID requestUUID, String fileName, String contentType, String folder) {
        return requestUUID.toString() + fileName + contentType + folder;
    }

    @Override
    public void saveFile(byte[] bytes, String path) {

    }
}