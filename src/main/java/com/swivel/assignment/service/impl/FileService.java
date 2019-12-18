package com.swivel.assignment.service.impl;

import java.io.IOException;
import java.io.InputStream;

public class FileService {
    public InputStream getFileFromResources(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}