package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.response.SingleSongResponse;
import com.example.musicstreaming.models.response.SongResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SongInterface {

    @GET("song")
    Call<SongResponse> getSong(@Header("Authorization") String token);

    @GET("song/{song_id}")
    Call<SingleSongResponse> getSingleSong(@Header("Authorization") String token, @Path("song_id") String song_id);

    @GET("song/album/{album_id}")
    Call<SongResponse> getAlbumSong(@Header("Authorization") String token, @Path("album_id") String album_id);

}
