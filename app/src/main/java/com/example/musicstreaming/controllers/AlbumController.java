package com.example.musicstreaming.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.response.AlbumResponse;
import com.example.musicstreaming.models.response.ArtistResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AlbumInterface;
import com.example.musicstreaming.network.api.ArtistInterface;
import com.example.musicstreaming.network.api.GenreInterface;
import com.example.musicstreaming.views.AlbumView;
import com.example.musicstreaming.views.GetArtistView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumController {
    Context context;
    AlbumView albumView;

    public AlbumController(Context context, AlbumView albumView) {
        this.context = context;
        this.albumView = albumView;
    }

    public void getAlbum(String token){
        AlbumInterface api = RetrofitClient.getRetrofit().create(AlbumInterface.class);
        Call<AlbumResponse> albumResponseCall = api.getAlbum(token);

        albumResponseCall.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if(response.body().getStatus() == 200){
                    albumView.albumList(response.body().getAlbumList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getArtistAlbum(String token, String artist_id){
        AlbumInterface api = RetrofitClient.getRetrofit().create(AlbumInterface.class);
        Call<AlbumResponse> albumResponseCall = api.getArtistAlbum(token, artist_id);

        albumResponseCall.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                Log.d("album", "onResponse: "+ response.body().getMessage());
                if(response.body().getStatus() == 200){
                    albumView.albumList(response.body().getAlbumList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getGenreAlbum(String token, String genre_id){
        GenreInterface api = RetrofitClient.getRetrofit().create(GenreInterface.class);
        Call<AlbumResponse> albumResponseCall = api.getGenreAlbum(token, genre_id);

        albumResponseCall.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if(response.body().getStatus() == 200){
                    albumView.albumList(response.body().getAlbumList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
