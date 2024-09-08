package com.github.plasmus777.service.authentication;

import com.github.plasmus777.model.authentication.AuthToken;
import com.github.plasmus777.model.user.Publisher;
import com.github.plasmus777.model.user.User;
import com.github.plasmus777.service.Service;
import com.github.plasmus777.service.user.UserService;

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
            if(currentToken == null){
                AuthToken authToken = new AuthToken(email, password);
                user = userService.searchExact(email, authToken);
                currentToken = authToken;
            } else {
                user = userService.searchExact(email, currentToken);
            }

            if(user != null){
                if(user.getAuthToken().isTokenValid()){
                    loggedUser = user;
                    return true;
                } else {
                    user.setAuthToken(currentToken);
                    loggedUser = user;
                    return true;
                }
            } else {
                System.out.println("O usuário não existe no banco de dados.");
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
}
