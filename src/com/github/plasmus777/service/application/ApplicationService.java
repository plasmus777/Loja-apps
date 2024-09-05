package com.github.plasmus777.service.application;

import com.github.plasmus777.model.application.Application;
import com.github.plasmus777.repository.ApplicationDatabase;
import com.github.plasmus777.service.Service;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService implements Service<Application> {

    private ApplicationDatabase applicationDatabase;
    private static long CURRENT_ID = 1;

    public ApplicationService(ApplicationDatabase applicationDatabase){
        setApplicationDatabase(applicationDatabase);
    }

    public ApplicationDatabase getApplicationDatabase() {
        return applicationDatabase;
    }

    public void setApplicationDatabase(ApplicationDatabase applicationDatabase) {
        this.applicationDatabase = applicationDatabase;
    }

    @Override
    public boolean save(Application application) {
        if(application == null){
            System.err.println("Não é possível adicionar um aplicativo nulo ao banco de dados.");
            return false;
        } else {
            if(application.getId() <= 0){
                System.err.println("O id do aplicativo é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getTitle() == null || application.getTitle().isBlank()){
                System.err.println("O título do aplicativo é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getDescription() == null || application.getDescription().isBlank()){
                System.err.println("A descrição do aplicativo é inválida e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getVersion() <= 0){
                System.err.println("A versão do aplicativo é inválida e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getPublisher() == null ||
                    application.getPublisher().getId() <= 0 ||
                    application.getPublisher().getAgencyName() == null || application.getPublisher().getAgencyName().isBlank() ||
                    application.getPublisher().getEmail() == null || application.getPublisher().getEmail().isBlank() ||
                    application.getPublisher().getUserName() == null || application.getPublisher().getUserName().isBlank() ||

                    application.getPublisher().getAuthToken() == null ||
                        application.getPublisher().getAuthToken().getEmail() == null ||
                        application.getPublisher().getAuthToken().getEmail().isBlank() ||
                        application.getPublisher().getAuthToken().getPassword() == null ||
                        application.getPublisher().getAuthToken().getPassword().isBlank()){
                System.err.println("O editor do aplicativo é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getCategories() == null || application.getCategories().length == 0){
                System.err.println("O aplicativo não possui categorias definidas e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getAboutUrl() == null || application.getAboutUrl().isBlank()) {
                System.err.println("A Url com informações sobre o aplicativo é inválida e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getDevelopmentUrl() == null || application.getDevelopmentUrl().isBlank()){
                    System.err.println("A Url com informações sobre o desenvolvimento do aplicativo é inválida e, portanto, não foi possível adicioná-lo ao banco de dados.");
                    return false;
            } else if(application.getMd5() == null || application.getMd5().isBlank()){
                System.err.println("O hash md5 do aplicativo é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(application.getSha256() == null || application.getSha256().isBlank()){
                System.err.println("O hash sha256 do aplicativo é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            }
        }

        //Check if the application has been saved successfully, attributing an id
        boolean returnValue = getApplicationDatabase().save(application);
        if(returnValue){
            application.setId(CURRENT_ID++);
        }

        return returnValue;
    }

    @Override
    public boolean update(Application application1, Application application2) {
        if(application1 == null || application2 == null){
            System.err.println("Não é possível atualizar um aplicativo com valores nulos.");
            return false;
        }

        if(application1.getId() <=0 ||
                application1.getTitle() == null || application1.getTitle().isBlank() ||
                application1.getDescription() == null || application1.getDescription().isBlank() ||
                application1.getVersion() <= 0 ||

                application1.getPublisher() == null ||
                application1.getPublisher().getId() <= 0 ||
                    application1.getPublisher().getAgencyName() == null || application1.getPublisher().getAgencyName().isBlank() ||
                    application1.getPublisher().getEmail() == null || application1.getPublisher().getEmail().isBlank() ||
                    application1.getPublisher().getUserName() == null || application1.getPublisher().getUserName().isBlank() ||

                    application1.getPublisher().getAuthToken() == null ||
                        application1.getPublisher().getAuthToken().getEmail() == null ||
                        application1.getPublisher().getAuthToken().getEmail().isBlank() ||
                        application1.getPublisher().getAuthToken().getPassword() == null ||
                        application1.getPublisher().getAuthToken().getPassword().isBlank() ||

                application1.getCategories() == null || application1.getCategories().length == 0 ||
                application1.getAboutUrl() == null || application1.getAboutUrl().isBlank() ||
                application1.getDevelopmentUrl() == null || application1.getDevelopmentUrl().isBlank() ||
                application1.getMd5() == null || application1.getMd5().isBlank() ||
                application1.getSha256() == null || application1.getSha256().isBlank()
                ){
            System.err.println("O aplicativo não pode ser atualizado, pois seus atributos são inválidos.");
            return false;
        }

        if(application2.getId() <=0 ||
                application2.getTitle() == null || application2.getTitle().isBlank() ||
                application2.getDescription() == null || application2.getDescription().isBlank() ||
                application2.getVersion() <= 0 ||

                application2.getPublisher() == null ||
                    application2.getPublisher().getId() <= 0 ||
                    application2.getPublisher().getAgencyName() == null || application2.getPublisher().getAgencyName().isBlank() ||
                    application2.getPublisher().getEmail() == null || application2.getPublisher().getEmail().isBlank() ||
                    application2.getPublisher().getUserName() == null || application2.getPublisher().getUserName().isBlank() ||

                    application2.getPublisher().getAuthToken() == null ||
                        application2.getPublisher().getAuthToken().getEmail() == null ||
                        application2.getPublisher().getAuthToken().getEmail().isBlank() ||
                        application2.getPublisher().getAuthToken().getPassword() == null ||
                        application2.getPublisher().getAuthToken().getPassword().isBlank() ||

                application2.getCategories() == null || application2.getCategories().length == 0 ||
                application2.getAboutUrl() == null || application2.getAboutUrl().isBlank() ||
                application2.getDevelopmentUrl() == null || application2.getDevelopmentUrl().isBlank() ||
                application2.getMd5() == null || application2.getMd5().isBlank() ||
                application2.getSha256() == null || application2.getSha256().isBlank()
        ){
            System.err.println("O aplicativo não pode ser atualizado, pois os atributos de atualização são inválidos.");
            return false;
        }

        return getApplicationDatabase().update(application1, application2);
    }

    @Override
    public boolean delete(Application application) {
        if(application == null ||
                application.getId() <=0 ||
                application.getTitle() == null || application.getTitle().isBlank() ||
                application.getDescription() == null || application.getDescription().isBlank() ||
                application.getVersion() <= 0 ||
                application.getPublisher() == null ||

                application.getPublisher().getId() <= 0 ||
                    application.getPublisher().getAgencyName() == null || application.getPublisher().getAgencyName().isBlank() ||
                    application.getPublisher().getEmail() == null || application.getPublisher().getEmail().isBlank() ||
                    application.getPublisher().getUserName() == null || application.getPublisher().getUserName().isBlank() ||

                    application.getPublisher().getAuthToken() == null ||
                    application.getPublisher().getAuthToken().getEmail() == null ||
                        application.getPublisher().getAuthToken().getEmail().isBlank() ||
                        application.getPublisher().getAuthToken().getPassword() == null ||
                        application.getPublisher().getAuthToken().getPassword().isBlank() ||

                application.getCategories() == null || application.getCategories().length == 0 ||
                application.getAboutUrl() == null || application.getAboutUrl().isBlank() ||
                application.getDevelopmentUrl() == null || application.getDevelopmentUrl().isBlank() ||
                application.getMd5() == null || application.getMd5().isBlank() ||
                application.getSha256() == null || application.getSha256().isBlank()){
            System.err.println("Não é possível apagar um aplicativo inválido do banco de dados.");
            return false;
        } else return getApplicationDatabase().delete(application);
    }

    @Override
    public Application search(long id) {
        if(id <= 0){
            System.err.println("Não é possível realizar uma busca com id <= 0.");
            return null;
        } else return getApplicationDatabase().search(id);
    }

    //Searches an application using a string - returns a list containing all applications that have matches in the search terms
    //(checking the application's title, publisher's agency name and description)
    @Override
    public List<Application> search(String search) {
        List<Application> results = new ArrayList<>();

        if(search == null || search.isBlank()){
            System.err.println("Não é possível realizar uma busca com termos de pesquisa nulos.");
            return results;
        }

        List<Application> applications = getApplicationDatabase().listAll();

        if(applications.isEmpty()){
            System.out.println("O banco de dados está vazio.");
        } else {
            for(Application a: applications){
                if(a.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                        a.getPublisher().getAgencyName().toLowerCase().contains(search.toLowerCase()) ||
                        a.getDescription().toLowerCase().contains(search.toLowerCase())){
                    results.add(a);
                }
            }
        }

        return results;
    }

    @Override
    public void listAll() {
        List<Application> list = getApplicationDatabase().listAll();
        if(!list.isEmpty()){
            System.out.println("===================================");
            System.out.println("     Aplicativos Cadastrados");
            System.out.println("===================================");
            for(Application a: list){
                System.out.println(a);
                System.out.println("===================================");
            }
        }
    }
}
