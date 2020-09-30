package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleAlbumResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private Album album;

    public SingleAlbumResponse(int status, String message, Album album) {
        this.status = status;
        this.message = message;
        this.album = album;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Album getAlbum() {
        return album;
    }
}
