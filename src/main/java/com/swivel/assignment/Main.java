package com.swivel.assignment;

import com.swivel.assignment.entity.*;
import com.swivel.assignment.service.UserService;
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
        System.out.println(user.getName());
    }
}
