package com.example.qrscanner;

public class Post {
    private String email;
    private String room;

    public Post(String email, String room){
        this.email = email;
        this.room = room;
    }

    public String getEmail() {
        return email;
    }

    public String getRoom() {
        return room;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
