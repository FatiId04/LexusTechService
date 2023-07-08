package com.example.lexuxtechservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lexuxtechservice.Response.Service2;

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
        holder.societe_txt.setText( data.get(position).getSociete());
        holder.service_txt.setText(data.get(position).getService());
        holder.imei_txt.setText(data.get(position).getImei());
        holder.sim_txt.setText(data.get(position).getSim());
        holder.marque_txt.setText(data.get(position).getVoiture());
        holder.matricule_txt.setText(data.get(position).getMatricule());
        holder.kilometrage_txt.setText(data.get(position).getKilometrage());
        holder.gps_txt.setText(data.get(position).getGps());
        holder.demarrage_txt.setText(data.get(position).getDemarrage());
        holder.date_txt.setText( data.get(position).getDate());
        holder.horaire_txt.setText( data.get(position).getHoraire());
        holder.horaire_txt.setText( data.get(position).getHoraire());
        holder.tech_txt.setText( data.get(position).getTechnicien());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ModelviewHolder extends RecyclerView.ViewHolder {
        TextView  societe_txt, service_txt, imei_txt,gps_txt,sim_txt,marque_txt,matricule_txt,
                kilometrage_txt,demarrage_txt,date_txt,horaire_txt,tech_txt;

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
            demarrage_txt = itemView.findViewById(R.id.anti_demarrage);
            date_txt= itemView.findViewById(R.id.date);
            horaire_txt= itemView.findViewById(R.id.horaire);
            tech_txt=itemView.findViewById((R.id.technicien));

        }
    }}