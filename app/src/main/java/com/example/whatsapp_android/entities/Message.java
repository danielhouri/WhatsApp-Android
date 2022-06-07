package com.example.whatsapp_android.entities;

import androidx.room.PrimaryKey;

import java.util.Date;

public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Contact;
    private String Content;
    private boolean send;
    private Date created;

    public Message(String contact, String content, boolean send, Date created) {
        Contact = contact;
        Content = content;
        this.send = send;
        this.created = created;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return Content;
    }

    public boolean isSend() {
        return send;
    }

    public Date getCreated() {
        return created;
    }
}
