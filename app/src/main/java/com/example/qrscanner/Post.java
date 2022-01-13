package com.example.qrscanner;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("email")
    private String email;
    @SerializedName("room_id")
    private String room_id;

    public Post(String email, String room_id){
        this.email = email;
        this.room_id = room_id;
    }

    public String getEmail() {
        return email;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
