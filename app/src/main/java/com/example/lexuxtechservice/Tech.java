package com.example.lexuxtechservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lexuxtechservice.Response.LoginResponse;
import com.example.lexuxtechservice.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tech extends AppCompatActivity {

    private Spinner serviceSpinner, demarrageSpinner;
    private EditText societeEditText, imeiEditText, simEditText, voitureEditText, matriculeEditText,
            kilometrageEditText, gpsEditText;

    private Button btnValider, btnPartager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_layout);

        societeEditText = findViewById(R.id.nom_de_societe);
        imeiEditText = findViewById(R.id.imei);
        simEditText = findViewById(R.id.carte_sim);
        voitureEditText = findViewById(R.id.marque_voiture);
        matriculeEditText = findViewById(R.id.matricule);
        kilometrageEditText = findViewById(R.id.kilometrage);
        gpsEditText = findViewById(R.id.marque_gps);
        btnValider = findViewById(R.id.valider);
        btnPartager = findViewById(R.id.partager);
        serviceSpinner = findViewById(R.id.type_de_service);
        demarrageSpinner = findViewById(R.id.anti_demarrage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_de_service, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.anti_demarrage, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        demarrageSpinner.setAdapter(adapter2);


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(societeEditText.getText().toString()) || TextUtils.isEmpty(serviceSpinner.getSelectedItem().toString())||TextUtils.isEmpty(imeiEditText.getText().toString())
                        || TextUtils.isEmpty(simEditText.getText().toString())||TextUtils.isEmpty(voitureEditText.getText().toString()) ||
                TextUtils.isEmpty(gpsEditText.getText().toString())||TextUtils.isEmpty(kilometrageEditText.getText().toString()) ||
                        TextUtils.isEmpty(matriculeEditText.getText().toString())||TextUtils.isEmpty(demarrageSpinner.getSelectedItem().toString())){
                    Toast.makeText(Tech.this,"Champs non remplis", Toast.LENGTH_LONG).show();
                }else{
                    register();

                }
            }
        });
    }


    public void register() {
        String service ;
        String demarrage ;
        String societe = societeEditText.getText().toString();
        String imei = imeiEditText.getText().toString();
        String sim = simEditText.getText().toString();
        String voiture = voitureEditText.getText().toString();
        String matricule = matriculeEditText.getText().toString();
        String kilometrage = kilometrageEditText.getText().toString();
        String gps = gpsEditText.getText().toString();


        service=serviceSpinner.getSelectedItem().toString();


        demarrage=demarrageSpinner.getSelectedItem().toString();

        Call<RegisterResponse> call = RetrofitClientRegister.getInstance().getApi().register(societe, service, imei, sim,
                voiture, matricule, kilometrage, gps, demarrage, "1");

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getError().equals(000) ){

                    Toast.makeText(Tech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Tech.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Tech.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



}
