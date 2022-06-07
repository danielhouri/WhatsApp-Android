package com.example.whatsapp_android.entities;


import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    @PrimaryKey()
    private String id;
    private String name, server, last, image;
    private Date lastDate;

    public Contact(String id, String name, String server, String last, String image, Date lastDate) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.image = image;
        this.lastDate = lastDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public String getLast() {
        return last;
    }

    public String getImage() {
        return image;
    }

    public Date getLastDate() {
        return lastDate;
    }
}
