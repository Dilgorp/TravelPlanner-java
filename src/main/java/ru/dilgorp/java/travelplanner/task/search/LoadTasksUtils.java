package ru.dilgorp.java.travelplanner.task.search;

import com.google.maps.ImageResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class LoadTasksUtils {
    public static String createImagePath(
            UUID requestUUID,
            String fileName,
            ImageResult imageResult,
            String photosFolder
    ) throws IOException {

        String directory = photosFolder
                + requestUUID.toString();
        String filePath = directory + "\\"
                + fileName + "."
                + imageResult.contentType.split("/")[1];

        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }

        return filePath;
    }

    public static void saveImage(ImageResult imageResult, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(imageResult.imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
