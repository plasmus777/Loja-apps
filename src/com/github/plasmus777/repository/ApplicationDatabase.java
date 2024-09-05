package com.github.plasmus777.repository;

import com.github.plasmus777.model.application.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDatabase implements Database<Application>{
    List<Application> applications;

    public ApplicationDatabase(){
        applications = new ArrayList<>();
    }



    @Override
    public boolean save(Application application) {
        if(application == null){
            System.err.println("Não é possível salvar um aplicativo nulo no banco de dados.");
            return false;
        } else if(applications.contains(application)){
            System.err.println("O aplicativo a ser salvo já existe no banco de dados.");
            return false;
        } else {
            applications.add(application);
            System.out.println("O aplicativo \"" + application.getTitle() + "\" foi adicionado ao banco de dados.");
            return true;
        }
    }

    @Override
    public boolean update(Application application1, Application application2) {
        if(application1 == null || !applications.contains(application1)){
            System.err.println("O aplicativo a ser atualizado não existe no banco de dados.");
            return false;
        } else if(application2 == null){
            System.err.println("O aplicativo não pode ser atualizado com um valor nulo.");
            return false;
        } else {
            System.out.println("O aplicativo \"" + application1.getTitle()+ "\" foi atualizado com as informações de: ");
            System.out.println(application1);
            application2.setId(application1.getId());
            applications.set(applications.indexOf(application1), application2);
            System.out.println("Para: ");
            System.out.println(application2);
            return true;
        }
    }

    @Override
    public boolean delete(Application application) {
        if(application == null || !applications.contains(application)){
            System.err.println("O aplicativo a ser removido não existe no banco de dados.");
            return false;
        } else {
            applications.remove(application);
            System.out.println("O aplicativo \"" + application.getTitle() + "\" foi removido do banco de dados.");
            return true;
        }
    }

    //Searches an application by its id - returns null if it does not exist
    @Override
    public Application search(long id) {
        if(applications.isEmpty()){
            System.out.println("O banco de dados está vazio.");
        } else {
            for(Application application: applications){
                if(application.getId() == id){
                    return application;
                }
            }
        }
        return null;
    }

    @Override
    public List<Application> listAll() {
        return applications;
    }
}
