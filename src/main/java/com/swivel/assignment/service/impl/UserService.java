package com.swivel.assignment.service.impl;

import java.io.BufferedReader;
import com.swivel.assignment.entity.User;
import com.swivel.assignment.service.EntityService;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonSyntaxException;

public class UserService implements EntityService<User> {
    @Override
    public List<User> findEntity(String fileName, String searchTerm, String searchValue) {
        List<User> userList = getEntityList(fileName);
        switch (searchTerm) {
            case "_id": {
                Integer id = Integer.parseInt(searchValue);
                List<User> users = userList.stream() 
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());
                return users;
            }
            case "name": {
                List<User> users = userList.stream() 
                .filter(x -> x.getName().toUpperCase().contains(searchValue.toUpperCase()))
                .collect(Collectors.toList());
                return users;
            }
            default: {
                return null;
            }
        } 
    }

    @Override
    public List<User> getEntityList(String fileName) {
        Gson gson = new Gson();
        FileService fs = new FileService();
        InputStream is = fs.getFileFromResources(fileName);
        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}