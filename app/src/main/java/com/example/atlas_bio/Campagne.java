package com.example.atlas_bio;

public class Campagne {
    private String titre;
    private String dateIn;
    private String dateOut;
    private String description;
    private String positionGPS;



    private String idCreator;

    public Campagne(String titre, String dateIn, String dateOut, String description, String positionGPS) {
        this.titre = titre;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.description = description;
        this.positionGPS = positionGPS;
    }

    public Campagne(){};


    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPositionGPS() {
        return positionGPS;
    }

    public void setPositionGPS(String positionGPS) {
        this.positionGPS = positionGPS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }
}
