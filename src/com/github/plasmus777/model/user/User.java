package com.github.plasmus777.model.user;

public class User {

    private long id;
    private String userName;
    private String email;
    private String password;

    public User(String userName, String email, String password){
        setUserName(userName);
        setEmail(email);
        setPassword(password);
    }

    //Unscrambles the saved password and returns it
    public String getPassword() {
        String[] chars = password.split("@");
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
        this.password = scrambledPassword;
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

    @Override
    public String toString() {
        return "Usuário: " + getUserName() + ", id = " + getId() +
                "\nE-mail: " + getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        //Object is not an User
        if(!(obj instanceof User))return false;

        //Verify class attributes
        if(this.getId() <= 0 || this.getUserName() == null || this.getEmail() == null || this.getPassword() == null)return false;

        //Verify obj (User) attributes
        User user = (User) obj;
        if(user.getId() <= 0 || user.getUserName() == null || user.getEmail() == null || user.getPassword() == null)return false;

        return (this.getId() == user.getId() && this.getUserName().equals(user.getUserName()) &&
                this.getEmail().equals(user.getEmail()) && this.getPassword().equals(user.getPassword()));

    }
}
