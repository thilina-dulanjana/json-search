package com.swivel.assignment;

import com.google.gson.annotations.SerializedName;
import com.swivel.assignment.entity.*;
import com.swivel.assignment.service.EntityService;
import com.swivel.assignment.service.impl.OrganizationService;
import com.swivel.assignment.service.impl.TicketService;
import com.swivel.assignment.service.impl.UserService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String intro = "Please select your search criteria\n"
                        + "1. Users\n"
                        + "2. Organizations\n"
                        + "3. Tickets\n";
        System.out.println(intro);
        int response = scanner.nextInt();
        System.out.println("Enter search term");
        String searchTerm = scanner.next();
        System.out.println("Enter search value");
        String searchValue = scanner.next();
        searchUser(searchTerm, searchValue);
    }

    private static void searchUser(String searchTerm, String searchValue) {
        UserService userService = new UserService();
        List<User> userList = userService.getUserList("jsonStore/users.json");
        User user = userService.findUser(userList, searchTerm, searchValue);

        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getAnnotation(SerializedName.class).value();
            Object value = null;
            try {
                value = field.get(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.printf("%s: %s%n", name, value);
        }


        OrganizationService organizationService = new OrganizationService();
        Organization organization = organizationService.findOrganization("jsonStore/organizations.json", "_id", user.getOrganizationId()+"");
        System.out.println("Organization: " + organization.getName());

        EntityService ticketService = new TicketService();
        Entity ticket = ticketService.findEntity("jsonStore/tickets.json", "submitter_id", user.getId()+"");
        System.out.println("Movie Title :" + ticket.getSubject());

    }
}
