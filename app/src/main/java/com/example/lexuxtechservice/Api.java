package com.example.lexuxtechservice;

import com.example.lexuxtechservice.Response.AjoutResponse;
import com.example.lexuxtechservice.Response.AjoutTechResponse;
import com.example.lexuxtechservice.Response.Boitier;
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
        //l'appel d'API de connexion
        @GET("login.php")
    Call<LoginResponse> login(
            @Query("identifiant") String identifiant,
            @Query("password") String password
    );

    @Headers({"Accept: application/json", "Content-Type:text/html"})
    //  l'appel d'API d'enregistrement des donnees concernant chaque service
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
            @Query("horaire") String horaire,
             @Query("technicien") String technicien
    );

    @Headers({"Accept: application/json", "Content-Type:text/html"})

    // l'appel d'API de récupération de touts les services effectues
    @GET("admin.php")
    Call<List<Service2 >> getData();

    @Headers({"Accept: application/json", "Content-Type:text/html"})
    @GET("ajout_boitier.php")
    Call<AjoutResponse> ajout(
            @Query("boitier") String boitier
    );

    @Headers({"Accept: application/json", "Content-Type:text/html"})

    // l'appel d'API de récupération de touts les boitiers
    @GET("getBoitier.php")
    Call<List<Boitier>> getData1();


    @Headers({"Accept: application/json", "Content-Type:text/html"})
    //  l'appel d'API d'enregistrement des techniciens
    @GET("ajout_tech.php")
    Call<AjoutTechResponse> register1(
            @Query("identifiant") String identifiant,
            @Query("password") String password

    );



}
