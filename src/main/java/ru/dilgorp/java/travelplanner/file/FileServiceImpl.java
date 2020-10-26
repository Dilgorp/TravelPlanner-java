package ru.dilgorp.java.travelplanner.file;

import org.springframework.stereotype.Service;
import ru.dilgorp.java.travelplanner.exception.IORuntimeException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public byte[] getBytes(String path) {
        byte[] result;
        if (path == null || path.isEmpty()) {
            return new byte[]{};
        }

        try (InputStream inputStream = new FileInputStream(path);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            outputStream.write(inputStream.readAllBytes());
            result = outputStream.toByteArray();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return result;
    }

    @Override
    public String createPath(UUID requestUUID, String fileName, String contentType, String folder) {
        String directory = folder
                + requestUUID.toString();

        Path directoryPath = Path.of(directory);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }

        return Path.of(directory, fileName + "." + contentType.split("/")[1]).toString();
    }

    @Override
    public void saveFile(byte[] bytes, String path) {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}
