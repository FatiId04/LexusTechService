package com.example.lexuxtechservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lexuxtechservice.Response.AjoutTechResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjoutTech  extends AppCompatActivity {

    private EditText identifiantEditText,passwordEditText;
    private Button ajout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_tech_layout);

        identifiantEditText= findViewById(R.id.id);
        passwordEditText=findViewById(R.id.mdp);
        ajout=findViewById(R.id.ajouter);

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                identifiantEditText.setText("");
                passwordEditText.setText("");
            }
        });

    }


    public void register() {

        String id = identifiantEditText.getText().toString();
        String mdp= passwordEditText.getText().toString();


        // Créer un appel à l'API avec les paramètres requis pour l'enregistrement
        Call<AjoutTechResponse> call = RetrofitAjoutTech.getInstance().getApi().register1(id,mdp);

        // Exécuter l'appel de manière asynchrone
        call.enqueue(new Callback<AjoutTechResponse>() {
            @Override
            public void onResponse(Call<AjoutTechResponse> call, Response<AjoutTechResponse> response) {
                // Récupèrer la réponse de l'appel
                AjoutTechResponse registerResponse = response.body();

                //Au cas ou l'erreur a pour valeur 000 , l enregistrement est effectue avec succe
                if (registerResponse.getError().equals(000) ){

                    Toast.makeText(AjoutTech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AjoutTech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AjoutTechResponse> call, Throwable t) {
                Toast.makeText(AjoutTech.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
