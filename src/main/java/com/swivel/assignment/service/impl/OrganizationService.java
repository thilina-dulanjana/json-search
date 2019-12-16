package com.swivel.assignment.service.impl;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.Organization;
import com.swivel.assignment.service.EntityService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationService implements EntityService<Organization> {
    @Override
    public List<Organization> findEntity(String fileName, String searchTerm, String searchValue) {
        List<Organization> orgList = getEntityList(fileName);
        if (searchTerm.equals("_id")) {
            Integer id = Integer.parseInt(searchValue);

            List<Organization> orgs = orgList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .collect(Collectors.toList());
            return orgs;
        }
        return null;
    }

    @Override
    public List<Organization> getEntityList(String fileName) {
        Gson gson = new Gson();
        FileService fs = new FileService();
        InputStream is = fs.getFileFromResources(fileName);
        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            Type orgListType = new TypeToken<ArrayList<Organization>>() {
            }.getType();
            return gson.fromJson(reader, orgListType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
