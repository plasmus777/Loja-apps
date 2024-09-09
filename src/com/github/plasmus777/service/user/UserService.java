package com.github.plasmus777.service.user;

import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.repository.Database;
import com.github.plasmus777.service.Service;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Service<User, AuthToken> {

    private Database<User> userDatabase;

    private static long CURRENT_ID = 1;

    public UserService(Database<User> userDatabase){
        setUserDatabase(userDatabase);
    }

    public Database<User> getUserDatabase() {
        return userDatabase;
    }

    public void setUserDatabase(Database<User> userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public boolean save(User user) {
        if(user == null){
            System.err.println("Não é possível adicionar um usuário nulo ao banco de dados.");
            return false;
        } else {
            if(user.getUserName() == null || user.getUserName().isBlank()){
                System.err.println("O nome do usuário é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(user.getEmail() == null || user.getEmail().isBlank()){
                System.err.println("O e-mail do usuário é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            } else if(user.getAuthToken() == null || user.getAuthToken().getEmail() == null || user.getAuthToken().getEmail().isBlank() ||
                        user.getAuthToken().getPassword() == null || user.getAuthToken().getPassword().isBlank()){
                System.err.println("O token do usuário é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                return false;
            }

            //Checks if the user is a publisher
            if(user instanceof Publisher){
                Publisher publisher = (Publisher) user;
                if(publisher.getAgencyName() == null || publisher.getAgencyName().isBlank()){
                    System.err.println("O nome da agência do editor é inválido e, portanto, não foi possível adicioná-lo ao banco de dados.");
                    return false;
                }
            }
        }

        //Check if the user has been saved successfully, attributing an id
        boolean returnValue = getUserDatabase().save(user);
        if(returnValue){
            user.setId(CURRENT_ID++);
        }

        return returnValue;
    }

    @Override
    public boolean update(User user1, User user2) {
        if(user1 == null || user2 == null){
            System.err.println("Não é possível atualizar um usuário com valores nulos.");
            return false;
        }

        if(user1.getId() <=0 ||
                user1.getEmail() == null || user1.getEmail().isBlank() ||
                user1.getUserName() == null || user1.getUserName().isBlank() ||
                user1.getAuthToken().getEmail().isBlank() ||
                user1.getAuthToken().getPassword() == null || user1.getAuthToken().getPassword().isBlank()){
            System.err.println("O usuário não pode ser atualizado, pois seus atributos são inválidos.");
            return false;
        }

        if(user2.getId() <=0 ||
                user2.getEmail() == null || user2.getEmail().isBlank() ||
                user2.getUserName() == null || user2.getUserName().isBlank() ||
                user2.getAuthToken().getEmail().isBlank() ||
                user2.getAuthToken().getPassword() == null || user2.getAuthToken().getPassword().isBlank()){
            System.err.println("O usuário não pode ser atualizado, pois os atributos de atualização são inválidos.");
            return false;
        }

        return getUserDatabase().update(user1, user2);
    }

    @Override
    public boolean delete(User user) {
        if(user == null ||
                user.getId() <= 0 ||
                user.getUserName() == null || user.getUserName().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getAuthToken().getEmail().isBlank() ||
                user.getAuthToken().getPassword() == null || user.getAuthToken().getPassword().isBlank()){
            System.err.println("Não é possível apagar um usuário inválido do banco de dados.");
            return false;
        } else return getUserDatabase().delete(user);
    }

    @Override
    public User search(long id) {
        if(id <= 0){
            System.err.println("Não é possível realizar uma busca com id <= 0.");
            return null;
        } else return getUserDatabase().search(id);
    }

    //Searches and return the user that has the exact same email and authentication token
    @Override
    public User searchExact(String email, AuthToken authToken){
        User u = null;

        if(email == null || email.isBlank() ||
        authToken == null || authToken.getEmail() == null || authToken.getEmail().isBlank() ||
        authToken.getPassword() == null || authToken.getPassword().isBlank() ||
        authToken.getExpirationDate() == null){
            System.err.println("Não é possível buscar um usuário exato no banco de dados com parâmetros inválidos.");
            return u;
        } else {
            List<User> users = getUserDatabase().listAll();
            if(users.isEmpty()){
                System.out.println("O banco de dados está vazio.");
            } else {
                for(User user: users){
                    if(user.getEmail().equals(email) && user.getAuthToken().equals(authToken)){
                        return user;
                    }
                }
            }

            return u;
        }
    }

    //Searches a user using a string - returns a list containing all users that have matches in the search terms
    //(checking username and e-mail)
    @Override
    public List<User> search(String search) {
        List<User> results = new ArrayList<>();

        if(search == null || search.isBlank()){
            System.err.println("Não é possível realizar uma busca com termos de pesquisa nulos.");
            return results;
        }

        List<User> users = getUserDatabase().listAll();

        if(users.isEmpty()){
            System.out.println("O banco de dados está vazio.");
        } else {
            for(User u: users){
                if(u.getUserName().toLowerCase().contains(search.toLowerCase()) ||
                u.getEmail().toLowerCase().contains(search.toLowerCase())){
                    results.add(u);
                }

                //Checks if the user is a publisher in order to search by its agency name as well
                if(u instanceof Publisher){
                    Publisher p = (Publisher) u;
                    if(p.getAgencyName().toLowerCase().contains(search.toLowerCase())){
                        results.add(p);
                    }
                }
            }
        }

        return results;
    }

    @Override
    public void listAll() {
        List<User> list = getUserDatabase().listAll();
        if(!list.isEmpty()){
            System.out.println("===================================");
            System.out.println("      Usuários Cadastrados");
            System.out.println("===================================");
            for(User u: list){
                System.out.println(u);
                System.out.println("===================================");
            }
        } else {
            System.out.println("Não há usuários cadastrados no sistema.");
        }
    }

    @Override
    public List<User> getList(){
        return userDatabase.listAll();
    }
}
