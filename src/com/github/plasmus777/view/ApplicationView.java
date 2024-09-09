package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.main.Main;
import com.github.plasmus777.model.application.Application;
import com.github.plasmus777.model.application.Category;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationView extends View{
    private final Service<Application, Publisher> applicationService;
    private final Scanner scanner;

    public ApplicationView(Service<Application, Publisher> applicationService, Scanner scanner){
        this.applicationService = applicationService;
        this.scanner = scanner;
    }


    @Override
    public void show() {
        cleanTerminal();
        boolean running = true;
        while(running){
            System.out.println("===================================");
            System.out.println("       Sistema de Aplicativos");
            System.out.println("===================================");
            if(Main.authService.hasLoggedPublisher()) {
                System.out.println("1 - Cadastrar Aplicativo");
                System.out.println("2 - Atualizar Dados do Aplicativo");
                System.out.println("3 - Apagar Aplicativo");
            }
            System.out.println("4 - Buscar Aplicativo");
            System.out.println("5 - Listar Aplicativos");
            System.out.println("6 - Retornar ao Menu Principal");

            int option = InputHelper.getValidOption(1, 6, scanner);

            switch(option){
                case 1:
                    if(Main.authService.hasLoggedPublisher()) {
                        registerApplication();
                    }
                    break;
                case 2:
                    if(Main.authService.hasLoggedPublisher()) {
                        updateApplication();
                    }
                    break;
                case 3:
                    if(Main.authService.hasLoggedPublisher()) {
                        deleteApplication();
                    }
                    break;
                case 4:
                    searchApplication();
                    break;
                case 5:
                    listApplications();
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

    private void registerApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("       Registrar Aplicativo");
        System.out.println("===================================");

        String title = InputHelper.getValidString("Por favor, informe um título para o aplicativo: ",
                "Título inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String description = InputHelper.getValidString("Por favor, informe uma descrição para o aplicativo: ",
                "Descrição inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        float version = InputHelper.getPositiveFloat("Por favor, informe a versão atual do aplicativo: ",
                "Versão inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        //hasLoggedPublisher == true -> current logged user will be the publisher
        Publisher publisher = (Publisher) Main.authService.getLoggedUser();

        boolean addingCategories = true;
        List<Category> categories = new ArrayList<>();
        do{
            System.out.println("Lista de categorias existentes: \nINTERNET, UTILITIES, SYSTEM, AUDIO, VIDEO, EDUCATION, GRAPHICS, OFFICE, DEVELOPMENT, PRODUCTIVITY, GAMES, OTHER\n");

            String categoryName = InputHelper.getValidString("Por favor, informe o nome da categoria para o aplicativo: ",
                    "Nome de categoria inválido! Por favor, tente novamente.", scanner);

            try {
                Category c = Category.valueOf(categoryName.toUpperCase());
                categories.add(c);
                addingCategories = false;
            } catch (IllegalArgumentException e){
                System.err.println("O aplicativo não pode pertencer a uma categoria inexistente.");
            }


            addingCategories = InputHelper.getValidBoolean("Você deseja adicionar o aplicativo a alguma categoria extra (responda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);


            if(categories.isEmpty()){
                System.out.println("O aplicativo deve pertencer obrigatoriamente a pelo menos uma categoria!");
            }

        } while(addingCategories || categories.isEmpty());

        cleanTerminal();

        String aboutUrl = InputHelper.getValidString("Por favor, informe uma url com informações sobre o aplicativo: ",
                "Url inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String developmentUrl = InputHelper.getValidString("Por favor, informe uma url com informações sobre o desenvolvimento do aplicativo: ",
                "Url inválida! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String md5 = InputHelper.getValidString("Por favor, informe o hash md5 do aplicativo: ",
                "Md5 inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        String sha256 = InputHelper.getValidString("Por favor, informe o hash sha256 do aplicativo: ",
                "Sha256 inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        Application application = new Application(title, description, version,
                publisher, categories.toArray(new Category[categories.size()]), aboutUrl,
                developmentUrl, md5, sha256);

        if(!applicationService.save(application)){
            System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi cadastrado devido a um erro.");
        };
    }

    private void updateApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("   Atualizar Dados do Aplicativo");
        System.out.println("===================================");

        String oldTitle = InputHelper.getValidString("Por favor, informe o título do aplicativo: ",
                "Título inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        //hasLoggedPublisher == true -> current logged user will be the publisher
        Publisher publisher = (Publisher) Main.authService.getLoggedUser();

        Application application = applicationService.searchExact(oldTitle, publisher);
        if (application == null) {
            System.out.println("O aplicativo não foi encontrado no banco de dados para o editor conectado e, portanto, não pode ser atualizado.");
        } else {
            Application newApplication;
            long id = application.getId();
            String newTitle = application.getTitle();
            String newDescription = application.getDescription();
            float newVersion = application.getVersion();
            Category[] newCategories = application.getCategories();
            String newAboutUrl = application.getAboutUrl();
            String newDevelopmentUrl = application.getDevelopmentUrl();
            String newMd5 = application.getMd5();
            String newSha256 = application.getSha256();

            boolean updating = true;
            while(updating){
                cleanTerminal();

                System.out.println("=========================================================");
                System.out.println("Atualizando o aplicativo \"" + application.getTitle() + "\"");
                System.out.println("=========================================================");
                System.out.println("1 - Concluir Atualização de Dados");
                System.out.println("2 - Atualizar Título");
                System.out.println("3 - Atualizar Descrição");
                System.out.println("4 - Atualizar Versão");
                System.out.println("5 - Atualizar Categorias");
                System.out.println("6 - Atualizar Url Sobre");
                System.out.println("7 - Atualizar Url Desenvolvimento");
                System.out.println("8 - Atualizar Hash Md5");
                System.out.println("9 - Atualizar Hash Sha256");

                int option = InputHelper.getValidOption(1, 9, scanner);
                switch(option){
                    case 1:
                        System.out.println("Concluindo Atualização de Dados...");
                        updating = false;
                        break;
                    case 2:
                        newTitle = InputHelper.getValidString("Por favor, informe um novo título para o aplicativo: ",
                                "Título inválido! Por favor, tente novamente.", scanner);
                        break;
                    case 3:
                        newDescription = InputHelper.getValidString("Por favor, informe uma novo descrição para o aplicativo: ",
                                "Descrição inválida! Por favor, tente novamente.", scanner);
                        break;
                    case 4:
                        newVersion = InputHelper.getPositiveFloat("Por favor, informe uma nova versão para o aplicativo: ",
                                "Versão inválida! Por favor, tente novamente.", scanner);
                        break;
                    case 5:
                        boolean addingCategories = true;
                        List<Category> categoriesAux = new ArrayList<>();
                        do{
                            System.out.println("Lista de categorias existentes: \nINTERNET, UTILITIES, SYSTEM, AUDIO, VIDEO, EDUCATION, GRAPHICS, OFFICE, DEVELOPMENT, PRODUCTIVITY, GAMES, OTHER\n");

                            String categoryName = InputHelper.getValidString("Por favor, informe o nome da categoria para o aplicativo: ",
                                    "Nome de categoria inválido! Por favor, tente novamente.", scanner);

                            try {
                                Category c = Category.valueOf(categoryName.toUpperCase());
                                categoriesAux.add(c);
                                addingCategories = false;
                            } catch (IllegalArgumentException e){
                                System.err.println("O aplicativo não pode pertencer a uma categoria inexistente.");
                            }


                            addingCategories = InputHelper.getValidBoolean("Você deseja adicionar o aplicativo a alguma categoria extra (responda com true - false)? ",
                                    "Resposta inválida! Por favor, tente novamente.", scanner);


                            if(categoriesAux.isEmpty()){
                                System.out.println("O aplicativo deve pertencer obrigatoriamente a pelo menos uma categoria!");
                            }

                        } while(addingCategories || categoriesAux.isEmpty());

                        newCategories = categoriesAux.toArray(new Category[categoriesAux.size()]);
                        break;
                    case 6:
                        newAboutUrl = InputHelper.getValidString("Por favor, informe uma nova url com informações sobre o aplicativo: ",
                                "Url inválida! Por favor, tente novamente.", scanner);
                        break;
                    case 7:
                        newDevelopmentUrl = InputHelper.getValidString("Por favor, informe uma nova url com informações sobre o desenvolvimento do aplicativo: ",
                                "Url inválida! Por favor, tente novamente.", scanner);
                        break;
                    case 8:
                        newMd5 = InputHelper.getValidString("Por favor, informe um novo hash md5 para o aplicativo: ",
                                "Md5 inválido! Por favor, tente novamente.", scanner);
                        break;
                    case 9:
                        newSha256 = InputHelper.getValidString("Por favor, informe um novo hash sha256 para o aplicativo: ",
                                "Sha256 inválido! Por favor, tente novamente.", scanner);
                        break;
                    default:
                        System.err.println("Opção inexistente!");
                        break;

                }
            }

            newApplication = new Application(newTitle, newDescription, newVersion, publisher, newCategories, newAboutUrl,
                    newDevelopmentUrl, newMd5, newSha256);
            newApplication.setId(id);

            if(!applicationService.update(application, newApplication)){
                System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi atualizado devido a um erro.");
            }
        }
    }

    private void deleteApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("        Apagar Aplicativo");
        System.out.println("===================================");

        String title = InputHelper.getValidString("Por favor, informe o título do aplicativo: ",
                "Título inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        //hasLoggedPublisher == true -> current logged user will be the publisher
        Publisher publisher = (Publisher) Main.authService.getLoggedUser();

        Application application = applicationService.searchExact(title, publisher);
        if (application == null) {
            System.out.println("O aplicativo não foi encontrado no banco de dados para o editor conectado e, portanto, não pode ser atualizado.");
        } else {
            System.out.println("================================================");
            System.out.println("Apagando o aplicativo \"" + application.getTitle() + "\"");
            System.out.println("================================================");
            System.out.println(application);
            System.out.println("================================================");
            boolean confirmDeletion = InputHelper.getValidBoolean("Você tem certeza de que deseja remover o aplicativo selecionado (reponda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);

            if(confirmDeletion){
                if(!applicationService.delete(application)){
                    System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi removido devido a um erro.");
                }
            }

        }
    }

    //Id, exact and list
    private void searchApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("         Buscar Aplicativo");
        System.out.println("===================================");

        boolean searching = true;
        while(searching){
            System.out.println("1 - Voltar ao Menu de Aplicativo");
            System.out.println("2 - Buscar Aplicativo por ID");
            System.out.println("3 - Buscar Aplicativo por Termos de Pesquisa");

            int option = InputHelper.getValidOption(1, 3, scanner);
            switch (option){
                case 1: //Return to main menu
                    System.out.println("Retornando ao Menu de Aplicativo...");
                    searching = false;
                    break;
                case 2: //Search by id
                    Application a = null;
                    long id = InputHelper.getPositiveLong("Por favor, informe o ID a ser pesquisado (> 0): ",
                            "ID inválido! Por favor, tente novamente.", scanner);
                    a = applicationService.search(id);
                    if(a != null){
                        System.out.println("===================================");
                        System.out.println("      Resultados da Pesquisa");
                        System.out.println("===================================");
                        System.out.println(a);
                        System.out.println("===================================");
                    } else {
                        System.out.println("===================================");
                        System.out.println("         Sem Resultados");
                        System.out.println("===================================");
                    }
                    break;
                case 3: //Search by generic String
                    String search = InputHelper.getValidString("Por favor, informe os termos de pesquisa: ",
                            "Termos de pesquisa inválidos! Por favor, tente novamente.", scanner);
                    List<Application> results = applicationService.search(search);
                    if(results != null && !results.isEmpty()){
                        System.out.println("===================================");
                        System.out.println("      Resultados da Pesquisa");
                        System.out.println("===================================");
                        for(Application app: results){
                            System.out.println(app);
                            System.out.println("===================================");
                        }
                    } else {
                        System.out.println("===================================");
                        System.out.println("         Sem Resultados");
                        System.out.println("===================================");
                    }
                    break;
                default:
                    System.err.println("Opção inexistente!");
            }
        }

    }

    private void listApplications(){
        cleanTerminal();
        applicationService.listAll();
    }
}
