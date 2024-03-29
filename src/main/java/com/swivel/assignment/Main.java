package com.swivel.assignment;

import com.google.gson.annotations.SerializedName;
import com.swivel.assignment.entity.Organization;
import com.swivel.assignment.entity.Ticket;
import com.swivel.assignment.entity.User;
import com.swivel.assignment.exception.UnsupportedSearchTermException;
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
        while (true) {
            String intro = "%nPlease select your search criteria%n"
                    + "1. Users%n"
                    + "2. Organizations%n"
                    + "3. Tickets%n"
                    + "%nTo view all possible searches type terms%n"
                    + "To exit the program type exit%n";

            System.out.printf(intro);
            String response = scanner.next();
            switch (response) {
                case "exit": {
                    System.exit(0);
                }
                case "terms": {
                    showPossiibleTerms();
                    continue;
                }
            }
            System.out.println("Enter search terms");
            String searchTerm = scanner.next();
            System.out.println("Enter search value");
            String searchValue = scanner.next();
            switch (response) {
                case "1": {
                    searchUser(searchTerm, searchValue);
                    break;
                }
                case "2": {
                    searchOrganization(searchTerm, searchValue);
                    break;
                }
                case "3": {
                    searchTicket(searchTerm, searchValue);
                    break;
                }
                default: {
                    System.out.println("Invalid option");
                }
            }
        }
    }

    private static void searchUser(String searchTerm, String searchValue) {
        try {
            EntityService<User> userService = new UserService();
            List<User> users = userService.findEntity("jsonStore/users.json", searchTerm, searchValue);

            for (User user : users) {
                printEntity(user);

                EntityService<Organization> organizationService = new OrganizationService();
                List<Organization> organizations = organizationService.findEntity("jsonStore/organizations.json", "_id", user.getOrganizationId() + "");
                for (Organization organization : organizations) {
                    System.out.printf("Organization: %s%n", organization.getName());
                }

                EntityService<Ticket> ticketService = new TicketService();
                List<Ticket> tickets = ticketService.findEntity("jsonStore/tickets.json", "submitter_id", user.getId() + "");
                int index = 0;
                for (Ticket ticket : tickets) {
                    System.out.printf("Movie Title_%s :%s%n", ++index, ticket.getSubject());
                }
            }
        } catch (UnsupportedSearchTermException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchOrganization(String searchTerm, String searchValue) {
        try {
            EntityService<Organization> organizationService = new OrganizationService();
            List<Organization> organizations = organizationService.findEntity("jsonStore/organizations.json", searchTerm, searchValue);

            for (Organization organization : organizations) {
                printEntity(organization);

                EntityService<User> userService = new UserService();
                List<User> users = userService.findEntity("jsonStore/users.json", "organization_id", organization.getId() + "");
                int index = 0;
                for (User user : users) {
                    System.out.printf("Organization_%s: %s%n ", ++index, user.getName());
                }

                EntityService<Ticket> ticketService = new TicketService();
                List<Ticket> tickets = ticketService.findEntity("jsonStore/tickets.json", "organization_id", organization.getId() + "");
                index = 0;
                for (Ticket ticket : tickets) {
                    System.out.printf("Movie Title_%s: %s%n", ++index, ticket.getSubject());
                }
            }
        } catch (UnsupportedSearchTermException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchTicket(String searchTerm, String searchValue) {
        try {
            EntityService<Ticket> ticketService = new TicketService();
            List<Ticket> tickets = ticketService.findEntity("jsonStore/tickets.json", searchTerm, searchValue);

            for (Ticket ticket : tickets) {
                printEntity(ticket);

                EntityService<User> userService = new UserService();
                List<User> users = userService.findEntity("jsonStore/users.json", "_id", ticket.getAssigneeId() + "");
                int index = 0;
                for (User user : users) {
                    System.out.printf("Assignee_%s: %s%n ", ++index, user.getName());
                }

                users = userService.findEntity("jsonStore/users.json", "_id", ticket.getSubmitterId() + "");
                index = 0;
                for (User user : users) {
                    System.out.printf("Submitter_%s: %s%n ", ++index, user.getName());
                }

                EntityService<Organization> organizationService = new OrganizationService();
                List<Organization> organizations = organizationService.findEntity("jsonStore/organizations.json", "_id", ticket.getOrganizationId() + "");
                for (Organization organization : organizations) {
                    System.out.printf("Organization: %s%n", organization.getName());
                }
            }
        } catch (UnsupportedSearchTermException e) {
            System.out.println(e.getMessage());
        }
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

    private static void showPossiibleTerms() {
        List<String> terms;
        terms = UserService.getSupportedTerms();
        System.out.printf("User Terms%n");
        for (String term : terms) {
            System.out.printf("%s%n", term);
        }

        terms = OrganizationService.getSupportedTerms();
        System.out.printf("%nOrganization Terms%n");
        for (String term : terms) {
            System.out.printf("%s%n", term);
        }

        terms = TicketService.getSupportedTerms();
        System.out.printf("%nTicket Terms%n");
        for (String term : terms) {
            System.out.printf("%s%n", term);
        }
    }
}
