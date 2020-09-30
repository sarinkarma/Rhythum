package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleArtistResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private Artist artist;

    public SingleArtistResponse(int status, String message, Artist artist) {
        this.status = status;
        this.message = message;
        this.artist = artist;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Artist getArtist() {
        return artist;
    }
}
