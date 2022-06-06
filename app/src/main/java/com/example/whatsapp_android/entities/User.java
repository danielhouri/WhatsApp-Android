package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User {
    @PrimaryKey()
    private String username;

    private String name, password, image;
    //private List<Contact> contacts;

    public User(String username, String name, String image, String password) {
        this.username = username;
        this.name = name;
        this.image = image;
        this.password = password;
        //this.contacts = contacts;
    }

    /*public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }*/

    /*public List<Contact> getContacts() {
        return contacts;
    }*/

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

