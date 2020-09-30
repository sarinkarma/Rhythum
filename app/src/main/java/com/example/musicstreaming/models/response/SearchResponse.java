package com.example.musicstreaming.models.response;

import com.example.musicstreaming.models.Album;
import com.example.musicstreaming.models.Search;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    private int status;
    private String message;

    @SerializedName("data")
    @Expose
    private Search search;

    public SearchResponse(int status, String message, Search search) {
        this.status = status;
        this.message = message;
        this.search = search;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Search getSearch() {
        return search;
    }
}
