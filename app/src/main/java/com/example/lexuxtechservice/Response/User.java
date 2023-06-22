package com.example.lexuxtechservice.Response;

public class User {

    String identifiant,password;

    public User(String identifiant, String password) {
        this.identifiant = identifiant;
        this.password = password;}

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMot_de_passe() {
        return password;
    }

    public void setMot_de_passe(String password) {
        this.password= password;
    }
}
