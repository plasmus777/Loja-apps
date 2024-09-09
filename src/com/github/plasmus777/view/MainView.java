package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.main.Main;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.service.authentication.AuthService;

import java.util.Scanner;

public class MainView extends View{

    private final static AuthService authService = Main.authService;

    private final View authView;
    private final View applicationView;
    private final View userView;
    private final Scanner scanner;

    public MainView(View authView, View applicationView, View userView, Scanner scanner){
        this.authView = authView;
        this.applicationView = applicationView;
        this.userView = userView;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        cleanTerminal();
        boolean running = true;
        while(running){
            System.out.println("===================================");
            System.out.println(" Loja de Aplicativos - versão 1.0");
            System.out.println("===================================");

            String session = (authService.hasLoggedUser() ? authService.getLoggedUser().getUserName() : "Desconectado");
            session += (authService.hasLoggedPublisher() ? " -> " + ((Publisher)authService.getLoggedUser()).getAgencyName() : "");
            System.out.println(session);
            System.out.println("===================================");

            System.out.println("1 - Aplicativos");
            System.out.println("2 - Usuários");
            System.out.println("3 - " + (authService.hasLoggedUser() ? "Logout" : "Login"));
            System.out.println("4 - Sair do sistema");

            int option = InputHelper.getValidOption(1, 4, scanner);

            switch (option){
                case 1:
                    applications();
                    break;
                case 2:
                    users();
                    break;
                case 3:
                    authentication();
                    break;
                case 4:
                    System.out.println("Obrigado por utilizar o sistema \"Loja de Aplicativos\"!");
                    System.out.println("Desenvolvedor - Fernando Lopes");
                    running = false;
                    break;
                default:
                    System.err.println("Opção inexistente!");
                    break;
            }
        }
    }

    public void authentication(){
        authView.show();
    }

    public void applications(){
        applicationView.show();
    }

    public void users(){
        userView.show();
    }
}
