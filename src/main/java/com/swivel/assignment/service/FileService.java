package com.swivel.assignment.service;

import java.io.InputStream;

public class FileService {
    public InputStream getFileFromResources(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        return inputStream;
    }
}