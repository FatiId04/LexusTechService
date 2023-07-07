package com.example.lexuxtechservice;

import com.example.lexuxtechservice.Response.LoginResponse;
import com.example.lexuxtechservice.Response.RegisterResponse;
import com.example.lexuxtechservice.Response.Service2;


import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
        @Headers({"Accept: application/json", "Content-Type:text/html"})
        @GET("login.php")
    Call<LoginResponse> login(
            @Query("identifiant") String identifiant,
            @Query("password") String password
    );

    @Headers({"Accept: application/json", "Content-Type:text/html"})
    @GET("register.php")
    Call<RegisterResponse> register(
            @Query("societe") String societe,
            @Query("service") String service,
            @Query("imei") String imei,
            @Query("sim") String sim,
            @Query("voiture") String voiture,
            @Query("matricule") String matricule,
            @Query("kilometrage") String kilometrage,
            @Query("gps") String gps,
            @Query("demarrage") String demarrage,
            @Query("localisation") String localisation,
            @Query("date") String date,
            @Query("horaire") String horaire
    );

    @Headers({"Accept: application/json", "Content-Type:text/html"})
    @GET("admin.php")
    Call<List<Service2 >> getData();



}
