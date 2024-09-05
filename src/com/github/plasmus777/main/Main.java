package com.github.plasmus777.main;

import com.github.plasmus777.repository.ApplicationDatabase;
import com.github.plasmus777.repository.UserDatabase;
import com.github.plasmus777.service.application.ApplicationService;
import com.github.plasmus777.service.user.UserService;

public class Main {
    public static void main(String[] args) {
        //Create databases for storing information
        UserDatabase users = new UserDatabase();
        ApplicationDatabase applications = new ApplicationDatabase();

        //Create services for interacting with the databases
        UserService userService = new UserService(users);
        ApplicationService applicationService = new ApplicationService(applications);


        System.out.println("Hello world!");
    }
}