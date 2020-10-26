package ru.dilgorp.java.travelplanner.file;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    @SneakyThrows
    public byte[] getBytes(String path) {
        byte[] result;
        if (path == null || path.isEmpty()) {
            return new byte[]{};
        }

        @Cleanup InputStream inputStream = new FileInputStream(path);
        @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(inputStream.readAllBytes());
        result = outputStream.toByteArray();
        return result;
    }

    @Override
    @SneakyThrows
    public String createPath(UUID requestUUID, String fileName, String contentType, String folder) {
        String directory = folder
                + requestUUID.toString();

        Path directoryPath = Path.of(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }

        return Path.of(directory, fileName + "." + contentType.split("/")[1]).toString();
    }

    @Override
    @SneakyThrows
    public void saveFile(byte[] bytes, String path) {
        @Cleanup OutputStream outputStream = new FileOutputStream(path);
        outputStream.write(bytes);
    }
}
