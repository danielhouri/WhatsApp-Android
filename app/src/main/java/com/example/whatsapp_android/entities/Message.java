package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Message(int id, String content, String username, boolean side, String time) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.side = side;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSide(boolean side) {
        this.side = side;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSide() {
        return side;
    }

    public String getTime() {
        return time;
    }

    private String content;
    private String username;
    private boolean side;
    private String time;



}
