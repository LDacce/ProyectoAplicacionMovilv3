package com.luis.proyectoaplicacionmovilv3.models;

public class CompanyModel {
    private long id;
    private String description;

    public CompanyModel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}