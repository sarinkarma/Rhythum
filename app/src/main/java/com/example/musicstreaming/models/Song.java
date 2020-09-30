package com.example.musicstreaming.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Song implements Serializable {
    private String _id;
    private String name;
    private String file;
    private String time;
    private ArrayList<String> tags;
    private Artist artist;
    private Album album;

    public Song(String _id, String name, String file, String time, ArrayList<String> tags, Artist artist, Album album) {
        this._id = _id;
        this.name = name;
        this.file = file;
        this.time = time;
        this.tags = tags;
        this.artist = artist;
        this.album = album;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
