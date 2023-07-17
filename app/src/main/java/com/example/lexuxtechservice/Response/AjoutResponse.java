package com.example.lexuxtechservice.Response;

public class AjoutResponse {


    String boitier,message,error;

    public AjoutResponse(String boitier, String message, String error) {
        this.boitier = boitier;
        this.message = message;
        this.error = error;
    }

    public String getBoitier() {
        return boitier;
    }

    public void setBoitier(String boitier) {
        this.boitier = boitier;
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
