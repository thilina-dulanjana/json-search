package com.swivel.assignment;

import com.swivel.assignment.entity.*;
import com.swivel.assignment.service.UserService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        List<User> userList = userService.getUserList("jsonStore/users.json");
        int id = 1;
        User user = userService.findUser(userList, id);
        System.out.println(user.getName());
    }
}
