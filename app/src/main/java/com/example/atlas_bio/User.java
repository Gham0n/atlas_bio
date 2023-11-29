package com.example.atlas_bio;

public class User {


    private String idCreator;

    private String name;

    private String email;

    public User(){}

    public User(String name, String email, String idCreator)
    {
        this.name = name;
        this.email = email;
        this.idCreator = idCreator;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }
}
