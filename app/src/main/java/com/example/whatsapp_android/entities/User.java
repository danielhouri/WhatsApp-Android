package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey()
    private String Username;

    private String Name, Password, Image;

    public User(String username, String name, String image, String password) {
        this.Username = username;
        this.Name = name;
        this.Image = image;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getPassword() {
        return Password;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}

