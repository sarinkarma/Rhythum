package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.response.AlbumResponse;
import com.example.musicstreaming.models.response.SingleAlbumResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AlbumInterface {

    @GET("album")
    Call<AlbumResponse> getAlbum(@Header("Authorization") String token);

    @GET("album/{album_id}")
    Call<SingleAlbumResponse> getSingleAlbum(@Header("Authorization") String token, @Path("album_id") String album_id);

    @GET("artist/album/{artist_id}")
    Call<AlbumResponse> getArtistAlbum(@Header("Authorization") String token, @Path("artist_id") String artist_id);

}
