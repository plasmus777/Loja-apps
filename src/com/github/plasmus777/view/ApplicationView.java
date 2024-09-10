package com.github.plasmus777.view;

import com.github.plasmus777.helper.InputHelper;
import com.github.plasmus777.main.Main;
import com.github.plasmus777.model.application.Application;
import com.github.plasmus777.model.application.Category;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.repository.ApplicationDatabase;
import com.github.plasmus777.service.Service;
import com.github.plasmus777.service.application.ApplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationView extends View{
    private final Service<Application, Publisher> applicationService;
    private final Scanner scanner;

    //Create a database/service for installed applications
    private final ApplicationDatabase installedApplicationsDatabase;
    private final Service<Application, Publisher> installedApplicationsService;

    public ApplicationView(Service<Application, Publisher> applicationService, Scanner scanner){
        this.applicationService = applicationService;
        this.scanner = scanner;

        //Initialize installed applications (database and service)
        installedApplicationsDatabase = new ApplicationDatabase();
        installedApplicationsService = new ApplicationService(installedApplicationsDatabase);
    }


    @Override
    public void show() {
        cleanTerminal();
        boolean running = true;
        while(running){
            System.out.println("===================================");
            System.out.println("       Sistema de Aplicativos");
            System.out.println("===================================");

            System.out.println("1 - Retornar ao Menu Principal");
            System.out.println("2 - Listar Aplicativos");
            System.out.println("3 - Buscar Aplicativo");
            System.out.println("4 - Listar Aplicativos Instalados");
            System.out.println("5 - Instalar Aplicativo");
            System.out.println("6 - Desinstalar Aplicativo");
            System.out.println("7 - Atualizar Aplicativos Instalados");
            if(Main.authService.hasLoggedPublisher()) {
                System.out.println("8 - Cadastrar Aplicativo");
                System.out.println("9 - Atualizar Dados do Aplicativo");
                System.out.println("10 - Apagar Aplicativo");
            }

            int option = InputHelper.getValidOption(1, 10, scanner);

            switch(option){
                case 1:
                    System.out.println("Retornando ao menu principal...");
                    running = false;
                    break;
                case 2:
                    listApplications();
                    break;
                case 3:
                    searchApplication();
                    break;
                case 4:
                    listInstalledApplications();
                    break;
                case 5:
                    installApplication();
                    break;
                case 6:
                    uninstallApplication();
                    break;
                case 7:
                    updateApplications();
                    break;
                case 8:
                    if(Main.authService.hasLoggedPublisher()) {
                        registerApplication();
                    } else System.out.println("Esta opção está disponível somente para usuários editores.");
                    break;
                case 9:
                    if(Main.authService.hasLoggedPublisher()) {
                        updateApplication();
                    } else System.out.println("Esta opção está disponível somente para usuários editores.");
                    break;
                case 10:
                    if(Main.authService.hasLoggedPublisher()) {
                        deleteApplication();
                    } else System.out.println("Esta opção está disponível somente para usuários editores.");
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

        //hasLoggedPublisher == true -> current logged user will be the publisher
        Publisher publisher = (Publisher) Main.authService.getLoggedUser();

        boolean addingCategories = true;
        List<Category> categories = new ArrayList<>();
        do{
            cleanTerminal();
            System.out.println("===================================");
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

            System.out.println("===================================");
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
        } else System.out.println("O aplicativo \"" + application.getTitle() + "\" foi cadastrado com sucesso.");

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
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

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
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
            boolean confirmDeletion = InputHelper.getValidBoolean("Você tem certeza de que deseja remover o aplicativo selecionado (responda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);

            if(confirmDeletion){
                if(!applicationService.delete(application)){
                    System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi removido devido a um erro.");
                } else System.out.println("O aplicativo \"" + application.getTitle() + "\" foi removido com sucesso.");
            }

        }

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
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

                    System.out.println("Pressione ENTER para continuar...");
                    scanner.nextLine();
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

                    System.out.println("Pressione ENTER para continuar...");
                    scanner.nextLine();
                    break;
                default:
                    System.err.println("Opção inexistente!");
            }
        }
    }

    private void listApplications(){
        cleanTerminal();
        applicationService.listAll();

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    //===================================Installed Applications==========================================================

    //List all applications installed in the user's machine (theoretically)
    private void listInstalledApplications(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("     Aplicativos Instalados");
        System.out.println("===================================");
        installedApplicationsService.listAll();

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    //Install new applications to the user's machine (theoretically)
    private void installApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("       Instalar Aplicativo");
        System.out.println("===================================");

        long id = InputHelper.getPositiveLong("Por favor, informe o ID do aplicativo a ser instalado: ",
                "ID inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        Application application = applicationService.search(id);
        if (application == null) {
            System.out.println("O aplicativo não foi encontrado no banco de dados e, portanto, não pode ser instalado.");
        } else {
            System.out.println("================================================");
            System.out.println("Instalando o aplicativo \"" + application.getTitle() + "\"");
            System.out.println("================================================");
            System.out.println(application);
            System.out.println("================================================");

            boolean confirmInstallation = InputHelper.getValidBoolean("Você tem certeza de que deseja instalar o aplicativo selecionado (responda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);

            if(confirmInstallation){
                if(!installedApplicationsService.save(application)) {
                    System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi instalado com sucesso devido a um erro.");
                } else System.out.println("O aplicativo \"" + application.getTitle() + "\" foi instalado com sucesso.");
            }
        }

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    //Remove an applications from the user's machine (theoretically)
    private void uninstallApplication(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("      Desinstalar Aplicativo");
        System.out.println("===================================");

        long id = InputHelper.getPositiveLong("Por favor, informe o ID do aplicativo a ser removido: ",
                "ID inválido! Por favor, tente novamente.", scanner);

        cleanTerminal();

        Application application = installedApplicationsService.search(id);
        if (application == null) {
            System.out.println("O aplicativo não foi encontrado na máquina local e, portanto, não pode ser removido.");
        } else {
            System.out.println("================================================");
            System.out.println("Desinstalando o aplicativo \"" + application.getTitle() + "\"");
            System.out.println("================================================");
            System.out.println(application);
            System.out.println("================================================");

            boolean confirmDeletion = InputHelper.getValidBoolean("Você tem certeza de que deseja desinstalar o aplicativo selecionado (responda com true - false)? ",
                    "Resposta inválida! Por favor, tente novamente.", scanner);

            if(confirmDeletion){
                if(!installedApplicationsService.delete(application)){
                    System.err.println("O aplicativo \"" + application.getTitle() + "\" não foi desinstalado devido a um erro.");
                } else System.out.println("O aplicativo \"" + application.getTitle() + "\" foi desinstalado com sucesso.");
            }
        }

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    //Update all applications in the user's machine (theoretically)
    private void updateApplications(){
        cleanTerminal();
        System.out.println("===================================");
        System.out.println("     Buscar por Atualizações");
        System.out.println("===================================");

        //Get updated apps from the store and compare them with the locally installed to update them (theoretically)
        List<Application> storeApps = applicationService.getList();
        List<Application> localApps = installedApplicationsService.getList();

        if(storeApps == null || storeApps.isEmpty()){
            System.out.println("Não foi possível buscar por atualizações.");
        } else if (localApps == null || localApps.isEmpty()){
            System.out.println("Não há aplicativos instalados.");
        } else {
            for(Application storeApp: storeApps){
                for(Application localApp: localApps){
                    if(storeApp.equals(localApp) && storeApp.compareTo(localApp) > 0){ //An update was found
                        cleanTerminal();
                        System.out.println("================================================");
                        System.out.println("Há uma atualização disponível para o aplicativo \"" + storeApp.getTitle() + "\":");
                        System.out.println("Versão " + localApp.getVersion() + " -> Versão " + storeApp.getVersion());
                        System.out.println("================================================");

                        boolean updateApp = InputHelper.getValidBoolean("Deseja atualizar o aplicativo (responda com true - false)? ",
                                "Resposta inválida! Por favor, tente novamente.", scanner);

                        if(updateApp){
                            cleanTerminal();
                            System.out.println("================================================");
                            System.out.println("Atualizando \"" + storeApp.getTitle() + "\"...");
                            if(!installedApplicationsService.update(localApp, storeApp)){
                                System.err.println("O aplicativo \"" + localApp.getTitle() + "\" não foi atualizado devido a um erro.");
                            } else System.out.println("O aplicativo \"" + localApp.getTitle() + "\" foi atualizado com sucesso.");
                            System.out.println("================================================");

                            System.out.println("Pressione ENTER para continuar...");
                            scanner.nextLine();
                        }
                    }
                }
            }
        }

        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}
