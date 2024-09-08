package com.github.plasmus777.main;

import com.github.plasmus777.repository.ApplicationDatabase;
import com.github.plasmus777.repository.UserDatabase;
import com.github.plasmus777.service.application.ApplicationService;
import com.github.plasmus777.service.user.UserService;
import com.github.plasmus777.view.UserView;
import com.github.plasmus777.view.View;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Create databases for storing information
        UserDatabase users = new UserDatabase();
        ApplicationDatabase applications = new ApplicationDatabase();

        //Create services for interacting with the databases
        UserService userService = new UserService(users);
        ApplicationService applicationService = new ApplicationService(applications);

        //Create scanner object to register terminal input
        Scanner scanner = new Scanner(System.in);

        //Utilize interactive views to run the program
        View userView = new UserView(userService, scanner);

        userView.show();
    }
}