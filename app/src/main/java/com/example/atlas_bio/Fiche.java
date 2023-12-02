package com.example.atlas_bio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fiche implements Serializable {


    private String espece;
    private String coordoneesGPS;
    private String date;
    private String heure;
    private String lieu;
    private String observation;
    private String imageUrl;

    private  String idCreator;

    private Map<String, Comment> comments;

    public Fiche() {
        this.comments = new HashMap<>();
    }
    public Fiche(String espece) {

        this.comments = new HashMap<>();
        this.espece = espece;
    }

    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Map<String, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<String, Comment> comments) {
        this.comments = comments;
    }
}
