package com.example.lexuxtechservice.Response;

public class AjoutTechResponse {
    String identifiant,password,message,error;

    public AjoutTechResponse(String identifiant, String password, String message, String error) {
        this.identifiant = identifiant;
        this.password = password;
        this.message = message;
        this.error = error;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
