package com.example.lexuxtechservice;

import com.example.lexuxtechservice.Response.LoginResponse;
import com.example.lexuxtechservice.Response.RegisterResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
            @Query("société") String societe,
            @Query("service") String service,
            @Query("IMEI") String imei,
            @Query("sim") String sim,
            @Query("voiture") String voiture,
            @Query("matricule") String matricule,
            @Query("kilométrage") String kilometrage,
            @Query("gps") String gps,
            @Query("démarrage") String demarrage,
            @Query("localisation") String localisation
    );



}
