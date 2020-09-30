package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private List<Genre> genreList;

    public GenreResponse(int status, String message, List<Genre> genreList) {
        this.status = status;
        this.message = message;
        this.genreList = genreList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}
