package com.example.lexuxtechservice;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class Login  extends AppCompatActivity {
    private EditText identifiantEditText , mot_de_passeEditText;
    private String identifiant,mot_de_passe;
    private TextView erreur;
    private Button connexion;
    private DatabaseManger databaseManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        identifiantEditText=findViewById(R.id.identifiant);
        mot_de_passeEditText=findViewById(R.id.mot_de_passe);
        connexion = findViewById(R.id.se_connecter);
        erreur=findViewById(R.id.erreur);

        databaseManger=new DatabaseManger(getApplicationContext());
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                identifiant=identifiantEditText.getText().toString();
                mot_de_passe=mot_de_passeEditText.getText().toString();
                connectUser();


            }
        });
    }
    public void onApiResponse(JSONObject response) throws JSONException {
        Boolean success = null;
        String error ="";

        success= response.getBoolean("success");
        try{
        if(success){
            Intent intent = new Intent(getApplicationContext(),Tech.class);
            startActivity(intent);


        }else{
            error = response.getString("error");
            erreur.setVisibility(View.VISIBLE);
            erreur.setText(error);

            }

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

        }

    }
    public void connectUser(){
        String url ="https://10.0.2.2/apiLexus/actions/connectUser.php";

        Map<String,String> params= new HashMap<>();
        params.put("identifiant", identifiant);
        params.put("mot de passe",mot_de_passe);
        JSONObject  parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    onApiResponse(response);
                    Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

        databaseManger.queue.add(jsonObjectRequest);
    }

}
