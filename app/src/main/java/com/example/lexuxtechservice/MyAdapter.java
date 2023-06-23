package com.example.lexuxtechservice;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lexuxtechservice.Response.Service2;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ModelviewHolder>{

    List<Service2> data;

    public MyAdapter(List<Service2> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ModelviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.admins_layout, parent, false);
        return new ModelviewHolder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull ModelviewHolder holder, int position) {
        holder.societe_txt.setText("Type de service: "+ data.get(position).getSociete());
        holder.service_txt.setText("Type de service: "+data.get(position).getService());
        holder.imei_txt.setText("IMEI: "+data.get(position).getImei());
        holder.sim_txt.setText("Numéro de carte sim: "+data.get(position).getSim());
        holder.marque_txt.setText("Marque de voiture: "+data.get(position).getVoiture());
        holder.matricule_txt.setText("Matricule: "+data.get(position).getMatricule());
        holder.kilometrage_txt.setText("Kilométarge: "+data.get(position).getKilometrage());
        holder.gps_txt.setText("Marque de GPS: "+data.get(position).getGps());
        holder.demarrage_txt.setText("Anti-Démarrage: "+data.get(position).getDemarrage());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ModelviewHolder extends RecyclerView.ViewHolder {
        TextView  societe_txt, service_txt, imei_txt,gps_txt,sim_txt,marque_txt,matricule_txt,
                kilometrage_txt,demarrage_txt;

        ModelviewHolder(@NonNull View itemView) {
            super(itemView);

            societe_txt = itemView.findViewById(R.id.nom_de_societe);
            service_txt = itemView.findViewById(R.id.type_de_service);
            imei_txt = itemView.findViewById(R.id.imei);
            gps_txt = itemView.findViewById(R.id.marque_gps);
            sim_txt = itemView.findViewById(R.id.carte_sim);
            marque_txt= itemView.findViewById(R.id.marque_voiture);
            matricule_txt = itemView.findViewById(R.id.matricule);
            kilometrage_txt = itemView.findViewById(R.id.kilometrage);
            demarrage_txt = itemView.findViewById(R.id.anti_demarrage);

        }
    }}