package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Song;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleSongResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private Song song;

    public SingleSongResponse(int status, String message, Song song) {
        this.status = status;
        this.message = message;
        this.song = song;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Song getSong() {
        return song;
    }
}
