package com.swivel.assignment.service.impl;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.Organization;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrganizationService {
    public Organization findOrganization(String fileName, String searchTerm, String searchValue) {
        List<Organization> orgList = getOrganizationsList(fileName);
        if (searchTerm.equals("_id")) {
            Integer id = Integer.parseInt(searchValue);

            Organization org = orgList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .findAny()
                    .orElse(null);
            return org;
        }
        return null;
    }

    public List<Organization> getOrganizationsList(String fileName) {
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
