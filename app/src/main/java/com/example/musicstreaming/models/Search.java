package com.example.musicstreaming.models;

import java.util.List;

public class Search {
    private List<Genre> genres;
    private List<Song> songs;
    private List<Album> albums;
    private List<Artist> artists;

    public Search(List<Genre> genres, List<Song> songs, List<Album> albums, List<Artist> artists) {
        this.genres = genres;
        this.songs = songs;
        this.albums = albums;
        this.artists = artists;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
