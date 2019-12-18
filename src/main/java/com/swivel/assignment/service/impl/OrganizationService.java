package com.swivel.assignment.service.impl;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.Organization;
import com.swivel.assignment.exception.UnsupportedSearchTermException;
import com.swivel.assignment.service.EntityService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationService implements EntityService<Organization> {
    private static List<String> supportedTerms = new ArrayList(Arrays.asList("_id", "name"));

    @Override
    public List<Organization> findEntity(String fileName, String searchTerm, String searchValue) throws UnsupportedSearchTermException {
        if (!getSupportedTerms().contains(searchTerm)) {
            throw new UnsupportedSearchTermException("Unsupported search term " + searchTerm);
        }

        List<Organization> orgList = getEntityList(fileName);
        switch (searchTerm) {
            case "_id": {
                Integer id = Integer.parseInt(searchValue);

                return orgList.stream()
                        .filter(x -> id.equals(x.getId()))
                        .collect(Collectors.toList());
            }
            case "name": {
                return orgList.stream()
                        .filter(x -> searchValue.equals(x.getName()))
                        .collect(Collectors.toList());
            }
            default: {
                throw new UnsupportedSearchTermException("Unsupported search term " + searchTerm);
            }
        }
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
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getSupportedTerms() {
        return supportedTerms;
    }
}
