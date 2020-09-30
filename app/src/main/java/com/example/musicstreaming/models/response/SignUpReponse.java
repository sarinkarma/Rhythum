package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpReponse {

    private int status;
    private String message;
    private String token;

    @SerializedName("data")
    @Expose
    private User user;

    public SignUpReponse(int status, String message, String token, User user) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
