package com.example.whatsapp_android.entities;

public class Invitation {
    String from, to, server;

    public Invitation(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
    }
}
