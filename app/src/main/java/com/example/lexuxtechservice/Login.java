package com.example.lexuxtechservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lexuxtechservice.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login  extends AppCompatActivity {
    private EditText identifiantEditText, passwordEditText;
    private String identifiant, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Masquer la barre d'action
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        identifiantEditText = findViewById(R.id.identifiant);
        passwordEditText = findViewById(R.id.mot_de_passe);
        btnLogin = findViewById(R.id.se_connecter);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérifie si les champs d'identifiant et de mot de passe sont vides
                if (TextUtils.isEmpty(identifiantEditText.getText().toString()) || TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    Toast.makeText(Login.this, "Champs non remplis", Toast.LENGTH_LONG).show();
                } else {
                    //proceder a la connexion
                    login();
                }

            }
        });
    }


    public void login() {

        identifiant = identifiantEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // Créer l'appel à l'API pour effectuer la connexion
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(identifiant, password);
        // Si l'identifiant est egale a "admin" , l utilisateur va etre diriger le layout de l administrateur
        if (identifiant.equals("admin")||identifiant.equals("admin ")) {
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getError().equals("200")) {
                        // Si la connexion est réussie,  l'activité admin sera lance
                        Intent intent = new Intent(Login.this, Admin.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (loginResponse.getError().equals("400")) {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            // Si l'identifiant est different de "admin" , l utilisateur va etre diriger le layout du technicien
        } else {
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getError().equals("200")) {
                        // Si la connexion est réussie,  l'activité Tech sera lance avec l'identifiant en extra
                        Intent intent1 = new Intent(Login.this, Tech.class);
                        Bundle b = new Bundle();
                        b.putString("technicien", identifiantEditText.getText().toString());
                        intent1.putExtras(b);
                        startActivity(intent1);
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (loginResponse.getError().equals("400")) {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}


