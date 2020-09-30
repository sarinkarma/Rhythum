package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Song;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private List<Song> songList;

    public SongResponse(int status, String message, List<Song> songList) {
        this.status = status;
        this.message = message;
        this.songList = songList;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Song> getSongList() {
        return songList;
    }
}
