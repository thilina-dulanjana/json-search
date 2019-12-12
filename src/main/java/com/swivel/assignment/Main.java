package com.swivel.assignment;

import com.swivel.assignment.entity.*;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Gson gson = new Gson();

        File file = new File("");//main.getFileFromResources("jsonStore\\users.json");

        try (Reader reader = new FileReader(file)) {

            // Convert JSON File to Java Object
            User user = gson.fromJson(reader, User.class);

            // print staff object
            System.out.println(user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getFileFromResources(String fileName) {
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        return file;
    }

}
