package com.github.plasmus777.model.user;

import com.github.plasmus777.model.authentication.AuthToken;

public class Publisher extends User {

    private String agencyName;
    private boolean verified;

    public Publisher(String userName, String email, AuthToken authToken, String agencyName, boolean verified) {
        super(userName, email, authToken);
        setAgencyName(agencyName);
        setVerified(verified);
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean isPublisher() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nNome do Editor: " + getAgencyName() +
                (isVerified() ? "\nEditor Verificado" : "\nEditor Desconhecido");
    }

    @Override
    public boolean equals(Object obj) {
        //Object is not a Publisher
        if(!(obj instanceof Publisher))return false;

        //Verify class attributes
        if(this.getAgencyName() == null)return false;

        //Verify obj (Publisher) attributes
        Publisher publisher = (Publisher) obj;
        if(publisher.getAgencyName() == null)return false;

        return (super.equals(obj) && this.getAgencyName().equals(publisher.getAgencyName()) && this.isVerified() == publisher.isVerified());
    }
}
