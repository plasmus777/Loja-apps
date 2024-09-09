package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.main.Main;
import com.github.plasmus777.service.authentication.AuthService;

import java.util.Scanner;

public class AuthView extends View{

    private final Scanner scanner;

    private final static AuthService authService = Main.authService;

    private final View userView;

    public AuthView(View userView, Scanner scanner){
        this.scanner = scanner;
        this.userView = userView;
    }

    @Override
    public void show() {
        cleanTerminal();
//        boolean running = true;
//        while(running) {
            cleanTerminal();

//            System.out.println("===================================");
//            System.out.println("        Sesssão Atual");
//            System.out.println("===================================");

            //Check if a user has logged in or not
            if (authService.hasLoggedUser()) {
                logOut();
//                showLoggedUserInfo();
//
//                System.out.println("1 - Retornar ao Menu Principal");
//                System.out.println("2 - Fazer Logout");
//
//                int option = InputHelper.getValidOption(1, 2, scanner);
//
//                switch (option){
//                    case 1:
//                        System.out.println("Retornando ao menu principal...");
//                        running = false;
//                        break;
//                    case 2:
//                        logOut();
//                        break;
//                    default:
//                        System.err.println("Opção inexistente!");
//                        break;
//                }
            } else {
                logIn();
            }
//        }
    }

    public void logIn(){
        System.out.println("===================================");
        System.out.println("             Login");
        System.out.println("===================================");

        System.out.println("1 - Fazer Login");
        System.out.println("2 - Ir ao Sistema de Usuários (Cadastro)");

        int option = InputHelper.getValidOption(1, 2, scanner);

        switch (option){
            case 1:
                String email = InputHelper.getValidString("Por favor, informe o e-mail do usuário: ",
                        "E-mail inválido! Por favor, tente novamente.", scanner);

                cleanTerminal();


                //Check if a password is required due to the authentication token mechanism - ask for user input if true
                String password = "password";
                if(authService.needsPassword(email)) {
                    password = InputHelper.getValidString("Por favor, informe a senha do usuário: ",
                            "Senha inválida! Por favor, tente novamente.", scanner);

                    cleanTerminal();
                }

                cleanTerminal();

                if(!authService.logIn(email, password)){
                    System.err.println("Ocorreu um erro ao realizar o login.");
                } else {
                    System.out.println("Login realizado com sucesso.");
                }

                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
                break;
            case 2:
                userView.show();
                break;
            default:
                System.err.println("Opção inexistente!");
                break;
        }
    }

    public void logOut(){
        System.out.println("===================================");
        System.out.println("             Logout");
        System.out.println("===================================");
        authService.logOut();

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void showLoggedUserInfo(){
        System.out.println("Usuário conectado: " + authService.getLoggedUser().getUserName());
        System.out.println("===================================");
        System.out.println(authService.getLoggedUser());
        System.out.println("===================================");
    }
}
