package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private List<Artist> artistList;

    public ArtistResponse(int status, String message, List<Artist> artistList) {
        this.status = status;
        this.message = message;
        this.artistList = artistList;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }
}
