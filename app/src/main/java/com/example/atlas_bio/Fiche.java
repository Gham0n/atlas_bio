package com.example.atlas_bio;

public class Fiche {


    private String espece;
    private String coordoneesGPS;
    private String date;
    private String heure;
    private String lieu;
    private String observation;

    public Fiche() {
    }



    public Fiche(String espece) {
        this.espece = espece;
    }

    public String getCoordoneesGPS() {
        return coordoneesGPS;
    }

    public void setCoordoneesGPS(String coordoneesGPS) {
        this.coordoneesGPS = coordoneesGPS;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
