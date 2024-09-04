package com.github.plasmus777.model;

import com.github.plasmus777.model.user.User;

import java.time.LocalDateTime;

public class AuthToken {

    private String email;
    private String scrambledPassword;
    private LocalDateTime expirationDate;

    //The AuthToken class is used to create authentication tokens that determine if the AuthService class will
    //require the user's password or not - that is, if the current logged user is still logged in
    public AuthToken(String email, String password){
        setEmail(email);
        setPassword(password);

        //Defines the token's expiration date as one day from when it is generated
        setExpirationDate(LocalDateTime.now().plusDays(1));
    }

    //Checks if the token is still valid at a certain point in time
    public boolean isTokenValid(){
        return LocalDateTime.now().isBefore(getExpirationDate());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Unscrambles the saved password and returns it
    public String getPassword() {
        String[] chars = scrambledPassword.split("@");
        String returnPassword = "";

        for(String s: chars){
            returnPassword += String.valueOf(Character.toChars(Integer.parseInt(s)));
        }

        return returnPassword;
    }

    //Saves a scrambled password to avoid saving it as plain text
    public void setPassword(String password) {
        String scrambledPassword = "";
        for(char c: password.toCharArray()){
            int value = c;

            scrambledPassword += c + "@";
        }
        this.scrambledPassword = scrambledPassword;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
