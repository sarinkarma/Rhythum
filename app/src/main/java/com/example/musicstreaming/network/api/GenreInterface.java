package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.response.AlbumResponse;
import com.example.musicstreaming.models.response.GenreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GenreInterface {

    @GET("genre")
    Call<GenreResponse> getGenre(@Header("Authorization") String token);

    @GET("genre/{genre_id}/album")
    Call<AlbumResponse> getGenreAlbum(@Header("Authorization") String token, @Path("genre_id") String genre_id);

}
