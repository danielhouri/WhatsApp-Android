package com.example.whatsapp_android.entities;

public class Transfer {
    private String from, to , content;

    public Transfer(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }
}
