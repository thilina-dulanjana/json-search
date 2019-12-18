package com.swivel.assignment.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.User;
import com.swivel.assignment.exception.UnsupportedSearchTermException;
import com.swivel.assignment.service.EntityService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements EntityService<User> {
    @Override
    public List<User> findEntity(String fileName, String searchTerm, String searchValue) throws UnsupportedSearchTermException {
        List<User> userList = getEntityList(fileName);
        switch (searchTerm) {
            case "_id": {
                Integer id = Integer.parseInt(searchValue);
                return userList.stream()
                        .filter(x -> x.getId().equals(id))
                        .collect(Collectors.toList());
            }
            case "name": {
                return userList.stream()
                        .filter(x -> x.getName().toUpperCase().contains(searchValue.toUpperCase()))
                        .collect(Collectors.toList());
            }
            case "organization_id": {
                Integer id = Integer.parseInt(searchValue);
                return userList.stream()
                        .filter(x -> id.equals(x.getOrganizationId()))
                        .collect(Collectors.toList());
            }
            default: {
                throw new UnsupportedSearchTermException("Unsupported search term " + searchTerm);
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
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}