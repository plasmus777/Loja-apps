package com.github.plasmus777.service;

import com.github.plasmus777.model.AuthToken;
import com.github.plasmus777.model.user.User;

public class AuthService {

    private User loggedUser;

    private AuthToken currentToken;


    public AuthService(){

    }

    public boolean logIn(String email, String password){

        return false;
    }

    public void logOut(){
        setLoggedUser(null);
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public AuthToken getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(AuthToken currentToken) {
        this.currentToken = currentToken;
    }
}
