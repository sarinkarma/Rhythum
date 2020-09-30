package com.example.musicstreaming.models.request;

public class SearchRequest {

    private String search;

    public SearchRequest(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
