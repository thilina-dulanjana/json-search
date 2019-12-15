package com.swivel.assignment.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.swivel.assignment.entity.Organization;
import com.swivel.assignment.entity.Ticket;
import com.swivel.assignment.service.EntityService;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements EntityService {

    @Override
    public Ticket findEntity(String fileName, String searchTerm, String searchValue) {
        List<Ticket> ticketList = getEntityList(fileName);
        if (searchTerm.equals("_id")) {
            Integer id = Integer.parseInt(searchValue);

            Ticket ticket = ticketList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .findAny()
                    .orElse(null);
            return ticket;
        }
        return null;
    }

    @Override
    public List getEntityList(String fileName) {
        Gson gson = new Gson();
        FileService fs = new FileService();
        InputStream is = fs.getFileFromResources(fileName);
        try (Reader reader = new BufferedReader(new InputStreamReader(is))) {
            Type ticketListType = new TypeToken<ArrayList<Ticket>>() {
            }.getType();
            return gson.fromJson(reader, ticketListType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
