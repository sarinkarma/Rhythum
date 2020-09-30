package com.example.musicstreaming.models.response;

public class LogoutResponse {

    private int status;
    private String message;

    public LogoutResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
