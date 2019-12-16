package com.swivel.assignment;

import com.google.gson.annotations.SerializedName;
import com.swivel.assignment.entity.Organization;
import com.swivel.assignment.entity.Ticket;
import com.swivel.assignment.entity.User;
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
        switch (response) {
            case 1: {
                searchUser(searchTerm, searchValue);
                break;
            }
            case 2: {
                searchOrganization(searchTerm, searchValue);
                break;
            }
            case 3: {
                searchTicket(searchTerm, searchValue);
                break;
            }
            default: {
                System.out.println("Invalid option");
            }
        }

    }

    private static void searchUser(String searchTerm, String searchValue) {
        EntityService<User> userService = new UserService();
        List<User> users = userService.findEntity("jsonStore/users.json", searchTerm, searchValue);

        for (User user : users) {
            printEntity(user);

            EntityService<Organization> organizationService = new OrganizationService();
            List<Organization> organizations = organizationService.findEntity("jsonStore/organizations.json", "_id", user.getOrganizationId() + "");
            for (Organization organization : organizations) {
                System.out.println("Organization: " + organization.getName());
            }

            EntityService<Ticket> ticketService = new TicketService();
            List<Ticket> tickets = ticketService.findEntity("jsonStore/tickets.json", "submitter_id", user.getId() + "");
            int index = 0;
            for (Ticket ticket : tickets) {
                System.out.printf("Movie Title_%s :%s%n", ++index, ticket.getSubject());
            }
        }
    }

    private static void searchOrganization(String searchTerm, String searchValue) {

    }

    private static void searchTicket(String searchTerm, String searchValue) {

    }

    private static <E> void printEntity(E entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getAnnotation(SerializedName.class).value();
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.printf("%s: %s%n", name, value);
        }
    }
}
