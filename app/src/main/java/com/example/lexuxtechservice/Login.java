package com.example.lexuxtechservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lexuxtechservice.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login  extends AppCompatActivity {
    private EditText identifiantEditText , passwordEditText;
    private String identifiant,password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        identifiantEditText = findViewById(R.id.identifiant);
        passwordEditText = findViewById(R.id.mot_de_passe);
        btnLogin=findViewById(R.id.se_connecter);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(identifiantEditText.getText().toString()) || TextUtils.isEmpty(passwordEditText.getText().toString())){
                    Toast.makeText(Login.this,"Champs non remplis", Toast.LENGTH_LONG).show();
                }else{
                    //proceed to login
                    login();
                }

            }
        });
    }


        public void login()
        {

            identifiant=identifiantEditText.getText().toString();
            password=passwordEditText.getText().toString();

            Call<LoginResponse> call=RetrofitClient.getInstance().getApi().login(identifiant,password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse=response.body();
                    if(response.isSuccessful()){
                        Intent intent = new Intent(Login.this,Tech.class);
                        startActivity(intent);
                        Toast.makeText(Login.this,loginResponse.getMessage(),Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_SHORT);

                }
            });
        }

}


