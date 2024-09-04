package com.github.plasmus777.model.user;

import com.github.plasmus777.model.AuthToken;

public class User {

    private long id;
    private String userName;
    private String email;
    private AuthToken authToken;

    public User(String userName, String email, AuthToken authToken){
        setUserName(userName);
        setEmail(email);
        setAuthToken(authToken);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Usuário: " + getUserName() + ", id = " + getId() +
                "\nE-mail: " + getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        //Object is not a User
        if(!(obj instanceof User))return false;

        //Verify class attributes
        if(this.getUserName() == null || this.getEmail() == null || this.getAuthToken() == null)return false;

        //Verify obj (User) attributes
        User user = (User) obj;
        if(user.getUserName() == null || user.getEmail() == null || user.getAuthToken() == null)return false;

        return (this.getUserName().equals(user.getUserName()) && this.getEmail().equals(user.getEmail()) &&
                this.getAuthToken().equals(user.getAuthToken()));

    }
}
