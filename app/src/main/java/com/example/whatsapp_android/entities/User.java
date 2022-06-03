package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username, nickname, image, lastMessage, time, server;

    public User(String username, String nickname, String image, String lastMessage, String time, String server) {
        this.username = username;
        this.nickname = nickname;
        this.image = image;
        this.lastMessage = lastMessage;
        this.time = time;
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImage() {
        return image;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public String getServer() {
        return server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setServer(String server) {
        this.server = server;
    }
}

