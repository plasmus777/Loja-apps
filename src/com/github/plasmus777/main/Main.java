package com.github.plasmus777.main;

import com.github.plasmus777.model.application.Application;
import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.repository.ApplicationDatabase;
import com.github.plasmus777.repository.UserDatabase;
import com.github.plasmus777.service.Service;
import com.github.plasmus777.service.application.ApplicationService;
import com.github.plasmus777.service.authentication.AuthService;
import com.github.plasmus777.service.user.UserService;
import com.github.plasmus777.view.*;

import java.util.Scanner;

public class Main {

    public static AuthService authService;

    public static void main(String[] args) {
        //Create databases for storing information
        UserDatabase users = new UserDatabase();
        ApplicationDatabase applications = new ApplicationDatabase();

        //Create services for interacting with the databases
        Service<User, AuthToken> userService = new UserService(users);
        Service<Application, Publisher> applicationService = new ApplicationService(applications);

        //Create scanner object to register terminal input
        Scanner scanner = new Scanner(System.in);

        //Initialize authService for user authentication purposes
        authService = new AuthService(userService);

        //Create interactive views to run the program
        View userView = new UserView(userService, scanner);//User management
        View applicationView = new ApplicationView(applicationService, scanner);//Application management
        View authView = new AuthView(userView, scanner);//Authentication management
        View mainView = new MainView(authView, applicationView, userView, scanner);//Main program execution

        //Run program through the main view
        mainView.show();

    }
}