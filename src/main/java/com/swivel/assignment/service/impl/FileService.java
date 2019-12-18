package com.swivel.assignment.service.impl;

import java.io.IOException;
import java.io.InputStream;

public class FileService {
    public InputStream getFileFromResources(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }
}