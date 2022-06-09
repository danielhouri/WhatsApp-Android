package com.example.whatsapp_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.whatsapp_android.utilities.DateConverter;

import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Contact;
    private String Content;
    private boolean send;

    @TypeConverters(DateConverter.class)
    private Date created;

    public Message() {

    }

    public Message(int id, String contact, String content, boolean send, Date created) {
        this.id = id;
        Contact = contact;
        Content = content;
        this.send = send;
        this.created = created;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
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
