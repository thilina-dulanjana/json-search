package com.swivel.assignment.service.impl;

import java.io.BufferedReader;
import com.swivel.assignment.entity.User;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonSyntaxException;

public class UserService {
    public List<User> getUserList(String fileName) {
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

    public User findUser(List<User> userList, String searchTerm, String searchValue) {
        if (searchTerm.equals("_id")) {
            Integer id = Integer.parseInt(searchValue);

            User user = userList.stream() 
                .filter(x -> x.getId().equals(id))
                .findAny()
                .orElse(null);
            return user;
        }
        return null;        
    }
}