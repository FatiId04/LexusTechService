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
    private EditText societeEditText,imeiEditText,simEditText,voitureEditText,matriculeEditText,
            kilometrageEditText,gpsEditText;

    private Button btnValider,btnPartager;
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
        btnValider=findViewById(R.id.valider);
        btnPartager=findViewById(R.id.partager);





        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    register();
            }
        });
    }


    public void register()
    {
        String service,demarrage;
        String societe=societeEditText.getText().toString();
        String  imei=imeiEditText.getText().toString();
        String sim=simEditText.getText().toString();
        String voiture=voitureEditText.getText().toString();
        String matricule=matriculeEditText.getText().toString();
        String kilometrage=kilometrageEditText.getText().toString();
        String gps=gpsEditText.getText().toString();

        String [] service_array = new String[]{"branchement","installation","changement de carte sim","changement de boitier","débranchement","programmation"};
        String [] demarrage_array = new String[]{"avec relai","sans relai","localisation"};

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,R.layout.drop_down_item,service_array);
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.type_de_service);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                service = adapterView.getItemAtPosition(i).toString();
            }
        });


        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this,R.layout.drop_down_item,demarrage_array);
        AutoCompleteTextView autoCompleteTextView2=findViewById(R.id.anti_demarrage);
        autoCompleteTextView2.setAdapter(adapter2);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                demarrage = adapterView.getItemAtPosition(i).toString();
            }
        });


        Call<RegisterResponse> call=RetrofitClientRegister.getInstance().getApi().register(societe,service,imei,sim,
                voiture,matricule,kilometrage,gps,demarrage,"1");

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse =response.body();
                if(response.isSuccessful()){

                    Toast.makeText(Tech.this,registerResponse.getMessage(),Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Tech.this,t.getMessage(),Toast.LENGTH_SHORT);

            }
        });
    }
}
