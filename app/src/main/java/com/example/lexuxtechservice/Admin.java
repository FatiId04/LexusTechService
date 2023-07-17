package com.example.lexuxtechservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lexuxtechservice.Response.Service2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    List<Service2> data1;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);



        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);


        // Supprimer le focus de la SearchView
        searchView.clearFocus();

        // Définir un écouteur pour la recherche de texte
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Appel à l'API pour récupérer les données
        Call< List<Service2> >call= RetrofitAdmin.getInstance().getApi().getData();

        call.enqueue(new Callback<List<Service2 >>() {
            @Override
            public void onResponse(Call< List<Service2 >> call, Response< List<Service2 >> response) {

                data1=response.body();
                adapter=new MyAdapter(data1);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call< List<Service2 >> call, Throwable t) {
                Toast.makeText(Admin.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.ajout:
                Intent intent = new Intent(Admin.this,Ajout.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                Intent intent1 = new Intent(Admin.this,Login.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Effectue une recherche dans la liste des services en fonction du text spécifié.
     * Les services correspondant sont ajoutés à une nouvelle liste et affichés dans le RecyclerView.
     *
     * @param text correspond au text de recherche
     */

    private void searchList(String text) {
        List<Service2> dataSearchList = new ArrayList<>();

        // Parcourir la liste des services pour trouver les correspondances avec le texte de recherche
        for (Service2 data : data1){
            if (data.getGps().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            } else if (data.getSim().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getService().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getImei().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getVoiture().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getMatricule().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getKilometrage().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getDemarrage().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getDate().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getHoraire().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getSociete().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }else if (data.getTechnicien().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        // Vérifier si des services correspondants ont été trouvés
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "service non enregistre", Toast.LENGTH_SHORT).show();
        } else {
            // Met à jour l'adaptateur avec la liste des services correspondants
            adapter.setSearchList(dataSearchList);
        }
    }

}
