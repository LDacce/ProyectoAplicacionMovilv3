package com.luis.proyectoaplicacionmovilv3.models;

import java.util.UUID;

public class UserModel {
    private String id;
    private String userName;

    private String password;


    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getUserName() { return userName; }
    public void setUserName(String value) { this.userName = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public UserModel(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
