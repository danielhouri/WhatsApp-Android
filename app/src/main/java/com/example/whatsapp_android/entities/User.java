package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey()
    private String username;

    private String name, password, image;

    public User(String username, String name, String image, String password) {
        this.username = username;
        this.name = name;
        this.image = image;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

