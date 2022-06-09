package com.example.whatsapp_android.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = {"id", "userid"})
public class Contact implements Serializable {
    @NonNull
    private String id;
    private String userid;
    private String name, server, last;

    private String lastDate;

    public Contact(@NonNull String id, String userid, String name, String server, String last, String lastDate) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastDate = lastDate;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @NonNull
    public String getUserid() {
        return userid;
    }

    public void setId(@NonNull String id) {
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

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @NonNull
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

    public String getLastDate() {
        return lastDate;
    }
}
