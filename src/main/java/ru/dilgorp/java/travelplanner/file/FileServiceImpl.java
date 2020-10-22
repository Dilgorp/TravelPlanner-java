package ru.dilgorp.java.travelplanner.file;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public byte[] getBytes(String path) {
        byte[] result;
        if (path == null || path.isEmpty()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(path);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            outputStream.write(inputStream.readAllBytes());
            result = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public String createPath(UUID requestUUID, String fileName, String contentType, String folder) {
        String directory = folder
                + requestUUID.toString();
        String filePath = directory + "\\"
                + fileName + "."
                + contentType.split("/")[1];

        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return filePath;
    }

    @Override
    public void saveFile(byte[] bytes, String path) {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
