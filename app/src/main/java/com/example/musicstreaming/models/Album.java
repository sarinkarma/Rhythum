package com.example.musicstreaming.models;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private String _id;
    private String name;
    private String release_date;
    private String copyright;
    private List<Genre> genre;
    private Artist artist;
    private String image;

    public Album(String _id, String name, String release_date, String copyright, List<Genre> genre, Artist artist, String image) {
        this._id = _id;
        this.name = name;
        this.release_date = release_date;
        this.copyright = copyright;
        this.genre = genre;
        this.artist = artist;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
