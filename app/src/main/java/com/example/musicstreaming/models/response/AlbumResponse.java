package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private List<Album> albumList;

    public AlbumResponse(int status, String message, List<Album> albumList) {
        this.status = status;
        this.message = message;
        this.albumList = albumList;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }
}
