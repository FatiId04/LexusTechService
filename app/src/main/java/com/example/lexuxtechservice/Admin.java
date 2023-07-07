package com.example.lexuxtechservice;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lexuxtechservice.Response.Service2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    public void displayData() {
        Call< List<Service2> >call= RetrofitAdmin.getInstance().getApi().getData();

        call.enqueue(new Callback<List<Service2 >>() {
            @Override
            public void onResponse(Call< List<Service2 >> call, Response< List<Service2 >> response) {

                List<Service2> data1=response.body();
                MyAdapter adapter=new MyAdapter(data1);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call< List<Service2 >> call, Throwable t) {
                Toast.makeText(Admin.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
