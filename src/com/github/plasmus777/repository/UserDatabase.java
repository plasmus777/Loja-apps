package com.github.plasmus777.repository;

import com.github.plasmus777.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase implements Database<User>{
    List<User> users;

    public UserDatabase(){
        users = new ArrayList<>();
    }

    @Override
    public boolean save(User user) {
        if(user == null){
            System.err.println("Não é possível salvar um usuário nulo no banco de dados.");
            return false;
        } else if(users.contains(user)){
            System.err.println("O usuário a ser salvo já existe no banco de dados.");
            return false;
        } else {
            users.add(user);
            System.out.println("O usuário \"" + user.getUserName() + "\" foi adicionado ao banco de dados.");
            return true;
        }
    }

    @Override
    public boolean update(User user1, User user2) {
        if(user1 == null || !users.contains(user1)){
            System.err.println("O usuário a ser atualizado não existe no banco de dados.");
            return false;
        } else if(user2 == null){
            System.err.println("O usuário não pode ser atualizado com um valor nulo.");
            return false;
        } else {
            System.out.println("O usuário \"" + user1.getUserName() + "\" foi atualizado com as informações de: ");
            System.out.println(user1);
            user2.setId(user1.getId());
            users.set(users.indexOf(user1), user2);
            System.out.println("Para: ");
            System.out.println(user2);
            return true;
        }
    }

    @Override
    public boolean delete(User user) {
        if(user == null || !users.contains(user)){
            System.err.println("O usuário a ser removido não existe no banco de dados.");
           return false;
        } else {
            users.remove(user);
            System.out.println("O usuário \"" + user.getUserName() + "\" foi removido do banco de dados.");
            return true;
        }
    }

    //Searches a user by its id - returns null if it does not exist
    @Override
    public User search(long id) {
        if(users.isEmpty()){
            System.out.println("O banco de dados está vazio.");
        } else {
            for(User user: users){
                if(user.getId() == id){
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public List<User> listAll() {
        return users;
    }
}
