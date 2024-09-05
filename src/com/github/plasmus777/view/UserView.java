package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.service.user.UserService;

import java.util.Scanner;

//Class responsible for managing User objects
public class UserView extends View{
    private UserService userService;
    private Scanner scanner;

    public UserView(UserService userService, Scanner scanner){
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        registerUser();
    }

    private void registerUser(){
        System.out.println("===================================");
        System.out.println("       Registrar Usuário");
        System.out.println("===================================");

        String userName = InputHelper.getValidString("Por favor, informe um apelido para o usuário: ",
                "Apelido inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String email = InputHelper.getValidString("Por favor, informe o e-mail do usuário: ",
                "E-mail inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        boolean confirmPassword = false;
        String password;
        do {
            password = InputHelper.getValidString("Por favor, informe uma senha para o usuário: ",
                    "Senha inválida! Por favor, tente novamente.", scanner);

            String passwordConfirm = InputHelper.getValidString("Por favor, confirme a senha do usuário: ",
                    "Senha inválida! Por favor, tente novamente.", scanner);

            confirmPassword = password.equals(passwordConfirm);
            if(!confirmPassword){
                System.out.println("As senhas não correspondem!");
            }
        } while (!confirmPassword);

        cleanTerminal();

        boolean userIsPublisher = InputHelper.getValidBoolean("O usuário é um editor (responda com true - false)? ",
                "Resposta inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        AuthToken token = new AuthToken(email, password);
        User user;
        if(userIsPublisher){
            String agencyName = InputHelper.getValidString("Por favor, informe o nome da agência do usuário editor: ",
                    "Nome inválido! Por favor, tente novamente.", scanner);

            cleanTerminal();

            boolean verified = InputHelper.getValidBoolean("O usuário editor é verificado (responda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);
            user = new Publisher(userName, email, token, agencyName, verified);
        } else {
            user = new User(userName, email, token);
        }

        cleanTerminal();

        userService.save(user);
    }

    private void updateUser(){

    }

    private void deleteUser(){

    }

    //Id, exact and list
    private void searchUser(){

    }
}
