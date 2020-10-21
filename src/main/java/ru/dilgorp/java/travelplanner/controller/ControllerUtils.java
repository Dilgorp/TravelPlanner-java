package ru.dilgorp.java.travelplanner.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ControllerUtils {
    static byte[] getImageBytes(String imagePath) {
        byte[] result = null;
        if(imagePath == null || imagePath.isEmpty()){
            return null;
        }

        try(InputStream inputStream = new FileInputStream(imagePath);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write(inputStream.readAllBytes());
            result = outputStream.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
