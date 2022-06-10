package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;

    private String created;

    private boolean send;
    private String userId, receiverId;

    public Message(int id, String content, String created, boolean send, String userId, String receiverId) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.send = send;
        this.userId = userId;
        this.receiverId = receiverId;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isSend() {
        return send;
    }

    public String getUserId() {
        return userId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }
}
