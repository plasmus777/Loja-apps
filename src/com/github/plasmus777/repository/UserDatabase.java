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
            System.err.println("Um usuário nulo não pode ser salvo no banco de dados.");
            return false;
        } else if(users.contains(user)){
            System.err.println("O usuário a ser salvo já existe no banco de dados.");
            return false;
        }

        users.add(user);

        return true;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User search(long id) {
        return null;
    }

    @Override
    public List<User> listAll() {
        return List.of();
    }
}
