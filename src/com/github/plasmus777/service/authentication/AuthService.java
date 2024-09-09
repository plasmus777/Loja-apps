package com.github.plasmus777.service.authentication;

import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.service.Service;

public class AuthService {

    private User loggedUser;
    private AuthToken currentToken;

    private final Service<User, AuthToken> userService;

    //Class used for authentication purposes within the system
    public AuthService(Service<User, AuthToken> userService){
        this.userService = userService;
    }

    //Method for defining a user as the current session's user
    public boolean logIn(String email, String password){
        if(getLoggedUser() == null){ //No user session
            User user;
            AuthToken authToken = new AuthToken(email, password);

            //Checks if the current token is valid to not need password input or not.
            if(needsPassword(email)){
                user = userService.searchExact(email, authToken);
            } else {
                user = userService.searchExact(email, currentToken);
            }

            if(user != null){
                if(user.getAuthToken().isTokenValid()){
                    loggedUser = user;
                    currentToken = user.getAuthToken();
                    return true;
                } else {
                    user.setAuthToken(authToken);
                    loggedUser = user;
                    currentToken = authToken;
                    return true;
                }
            } else {
                System.out.println("Dados de login incorretos; o usuário não existe.");
                return false;
            }
        } else {
            System.out.println("O usuário \"" + getLoggedUser().getUserName() + "\" já está conectado!");
            return false;
        }
    }

    public void logOut(){
        if(loggedUser != null){
            System.out.println("Usuário \"" + getLoggedUser().getUserName() +  "\" desconectado.");
            loggedUser = null;
        }
    }

    public User getLoggedUser(){
        return loggedUser;
    }

    //Checks if a user has logged in
    public boolean hasLoggedUser(){
        if (loggedUser != null) return true;
        else return false;
    }

    public boolean hasLoggedPublisher(){
        if(hasLoggedUser()){
            return getLoggedUser() instanceof Publisher;
        } else return false;
    }

    //Method to verify if password inputs are necessary (that is, the current authentication token does not work)
    public boolean needsPassword(String email){
        if(currentToken == null || !currentToken.isTokenValid()){
            return true;
        } else {
            User user = userService.searchExact(email, currentToken);

            if (user != null && user.getAuthToken().isTokenValid()) {
                return false;
            } else return true;
        }
    }
}
