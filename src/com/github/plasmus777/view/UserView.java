package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.service.user.UserService;

import java.util.Scanner;

//Class responsible for managing User objects
public class UserView extends View{
    private final UserService userService;
    private final Scanner scanner;

    public UserView(UserService userService, Scanner scanner){
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        boolean running = true;
        while(running){
            System.out.println("===================================");
            System.out.println("       Sistema de Usuários");
            System.out.println("===================================");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Atualizar Usuário");
            System.out.println("3 - Apagar Usuário");
            System.out.println("4 - Buscar Usuário");
            System.out.println("5 - Listar Usuários");
            System.out.println("6 - Retornar ao Menu Principal");

            int option = InputHelper.getValidOption(1, 6, scanner);

            switch(option){
                case 1:
                    registerUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    listUsers();
                    break;
                case 6:
                    System.out.println("Retornando ao menu principal...");
                    running = false;
                    break;
                default:
                    System.err.println("Opção inexistente!");
                    break;

            }
        }
    }

    private void registerUser(){
        cleanTerminal();
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

        if(!userService.save(user)){
            System.err.println("O usuário \"" + user.getUserName() + "\" não foi cadastrado devido a um erro.");
        };
    }

    private void updateUser(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("       Atualizar Usuário");
        System.out.println("===================================");

        String oldEmail = InputHelper.getValidString("Por favor, informe o e-mail do usuário: ",
                "E-mail inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String oldPassword = InputHelper.getValidString("Por favor, informe a senha do usuário: ",
                "Senha inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        AuthToken token = new AuthToken(oldEmail, oldPassword);
        User user = userService.searchExact(oldEmail, token);
        if (user == null) {
            System.out.println("O usuário não foi encontrado no banco de dados e, portanto, não pode ser atualizado.");
        } else {
            User newUser;
            long id = user.getId();
            String newUserName = user.getUserName();
            String newEmail = user.getEmail();
            String newPassword = user.getAuthToken().getPassword();

            String newAgencyName = "";
            boolean newVerified = false;
            if(user instanceof Publisher){
                Publisher publisher = (Publisher) user;
                newAgencyName = publisher.getAgencyName();
                newVerified = publisher.isVerified();
            }

            boolean updating = true;
            while(updating){
                cleanTerminal();
                System.out.println("=========================================================");
                System.out.println("Atualizando o usuário \"" + user.getUserName() + "\"");
                System.out.println("=========================================================");
                System.out.println("1 - Concluir Atualização");
                System.out.println("2 - Atualizar Nome de Usuário");
                System.out.println("3 - Atualizar E-mail");
                System.out.println("4 - Atualizar Senha");

                if(user instanceof Publisher){
                    System.out.println("5 - Atualizar Nome do Editor");
                    System.out.println("6 - Atualizar Status de Verificação");
                }

                int option = InputHelper.getValidOption(1, 6, scanner);
                switch(option){
                    case 1:
                        System.out.println("Concluindo Atualização...");
                        updating = false;
                        break;
                    case 2:
                        newUserName = InputHelper.getValidString("Por favor, informe um novo apelido para o usuário: ",
                                "Apelido inválido! Por favor, tente novamente.", scanner);
                        break;
                    case 3:
                        newEmail = InputHelper.getValidString("Por favor, informe um novo e-mail do usuário: ",
                                "E-mail inválido! Por favor, tente novamente.", scanner);
                        break;
                    case 4:
                        boolean confirmPassword = false;
                        do {
                            newPassword = InputHelper.getValidString("Por favor, informe uma nova senha para o usuário: ",
                                    "Senha inválida! Por favor, tente novamente.", scanner);

                            String passwordConfirm = InputHelper.getValidString("Por favor, confirme a nova senha do usuário: ",
                                    "Senha inválida! Por favor, tente novamente.", scanner);

                            confirmPassword = newPassword.equals(passwordConfirm);
                            if(!confirmPassword){
                                System.out.println("As senhas não correspondem!");
                            }
                        } while (!confirmPassword);
                        break;
                    case 5:
                        newAgencyName = InputHelper.getValidString("Por favor, informe o novo nome da agência do usuário editor: ",
                                "Nome inválido! Por favor, tente novamente.", scanner);
                        break;
                    case 6:
                        newVerified = InputHelper.getValidBoolean("O usuário editor é verificado (responda com true - false)? ",
                                "Resposta inválida! Por favor, tente novamente.", scanner);
                        break;
                    default:
                        System.err.println("Opção inexistente!");
                        break;

                }
            }

            AuthToken newAuthToken = new AuthToken(newEmail, newPassword);
            if(user instanceof Publisher){
                newUser = new Publisher(newUserName, newEmail, newAuthToken, newAgencyName, newVerified);
                newUser.setId(id);
            } else {
                newUser = new User(newUserName, newEmail, newAuthToken);
                newUser.setId(id);
            }

            if(!userService.update(user, newUser)){
                System.err.println("O usuário \"" + user.getUserName() + "\" não foi atualizado devido a um erro.");
            }
        }
    }

    private void deleteUser(){

    }

    //Id, exact and list
    private void searchUser(){

    }

    private void listUsers(){
        cleanTerminal();
        userService.listAll();
    }
}
