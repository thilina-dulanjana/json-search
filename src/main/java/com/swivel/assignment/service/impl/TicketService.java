package com.swivel.assignment.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.Ticket;
import com.swivel.assignment.exception.UnsupportedSearchTermException;
import com.swivel.assignment.service.EntityService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TicketService implements EntityService<Ticket> {
    private static List<String> supportedTerms = new ArrayList(Arrays.asList("_id", "submitter_id", "organization_id", "subject"));

    @Override
    public List<Ticket> findEntity(String fileName, String searchTerm, String searchValue) throws UnsupportedSearchTermException {
        if (!getSupportedTerms().contains(searchTerm)) {
            throw new UnsupportedSearchTermException("Unsupported search term " + searchTerm);
        }

        List<Ticket> ticketList = getEntityList(fileName);
        switch (searchTerm) {
            case "_id": {
                return ticketList.stream()
                        .filter(x -> searchValue.equals(x.getId()))
                        .collect(Collectors.toList());
            }
            case "submitter_id": {
                Integer id = Integer.parseInt(searchValue);
                return ticketList.stream()
                        .filter(x -> id.equals(x.getSubmitterId()))
                        .collect(Collectors.toList());
            }
            case "organization_id": {
                Integer id = Integer.parseInt(searchValue);
                return ticketList.stream()
                        .filter(x -> id.equals(x.getOrganizationId()))
                        .collect(Collectors.toList());
            }
            case "subject": {
                return ticketList.stream()
                        .filter(x -> x.getSubject().toUpperCase().contains(searchValue.toUpperCase()))
                        .collect(Collectors.toList());
            }
            default: {
                throw new UnsupportedSearchTermException("Unsupported search term " + searchTerm);
            }
        }
    }

    @Override
    public List<Ticket> getEntityList(String fileName) {
        Gson gson = new Gson();
        FileService fs = new FileService();
        InputStream is = fs.getFileFromResources(fileName);
        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            Type ticketListType = new TypeToken<ArrayList<Ticket>>() {
            }.getType();
            return gson.fromJson(reader, ticketListType);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getSupportedTerms() {
        return supportedTerms;
    }
}
