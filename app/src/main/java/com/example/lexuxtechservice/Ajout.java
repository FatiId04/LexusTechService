package com.example.lexuxtechservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lexuxtechservice.Response.AjoutResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ajout extends AppCompatActivity {

    private EditText boitierEditText;
    private Button ajout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_layout);

        boitierEditText= findViewById(R.id.boitier);
        ajout=findViewById(R.id.ajouter);

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                boitierEditText.setText("");
            }
        });

    }


    public void register() {

        String boitier = boitierEditText.getText().toString();


        // Créer un appel à l'API avec les paramètres requis pour l'enregistrement
        Call<AjoutResponse> call = RetrofitAjout.getInstance().getApi().ajout(boitier);

        // Exécuter l'appel de manière asynchrone
        call.enqueue(new Callback<AjoutResponse>() {
            @Override
            public void onResponse(Call<AjoutResponse> call, Response<AjoutResponse> response) {
                // Récupèrer la réponse de l'appel
                AjoutResponse registerResponse = response.body();

                //Au cas ou l'erreur a pour valeur 000 , l enregistrement est effectue avec succe
                if (registerResponse.getError().equals(000) ){

                    Toast.makeText(Ajout.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Ajout.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AjoutResponse> call, Throwable t) {
                Toast.makeText(Ajout.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
