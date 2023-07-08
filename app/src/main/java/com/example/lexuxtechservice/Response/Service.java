package com.example.lexuxtechservice.Response;

public class Service {
    String  societe,service,imei,sim,
            voiture,matricule,kilometrage,gps,demarrage,localisation,date,horaire,technicien;

    public Service(String societe, String service, String imei, String sim, String voiture, String matricule, String kilometrage,
                   String gps, String demarrage, String localisation,String horaire,String date,String technicien) {
        this.societe = societe;
        this.service = service;
        this.imei = imei;
        this.sim = sim;
        this.voiture = voiture;
        this.matricule = matricule;
        this.kilometrage = kilometrage;
        this.gps = gps;
        this.demarrage = demarrage;
        this.localisation = localisation;
        this.date=date;
        this.horaire=horaire;
        this.technicien=technicien;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getVoiture() {
        return voiture;
    }

    public void setVoiture(String voiture) {
        this.voiture = voiture;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(String kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getDemarrage() {
        return demarrage;
    }

    public void setDemarrage(String demarrage) {
        this.demarrage = demarrage;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getTechnicien() {
        return technicien;
    }

    public void setTechnicien(String technicien) {
        this.technicien = technicien;
    }
}
