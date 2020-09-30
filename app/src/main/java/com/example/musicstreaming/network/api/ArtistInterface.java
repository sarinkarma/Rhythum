package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.response.AlbumResponse;
import com.example.musicstreaming.models.response.ArtistResponse;
import com.example.musicstreaming.models.response.SingleArtistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ArtistInterface {

    @GET("artist")
    Call<ArtistResponse> getArtist(@Header("Authorization") String token);

    @GET("artist/{artist_id}")
    Call<SingleArtistResponse> getSingleArtist(@Header("Authorization") String token, @Path("artist_id") String artist_id);

}
