package com.example.lexuxtechservice.Response;

public class RegisterResponse {

    Service service;
    String message,error;

    public RegisterResponse(Service service, String message, String error) {
        this.service = service;
        this.message = message;
        this.error = error;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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
