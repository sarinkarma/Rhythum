package com.example.musicstreaming.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.response.SongResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.SongInterface;
import com.example.musicstreaming.views.SongView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongController {
    Context context;
    SongView songView;

    public SongController(Context context, SongView songView) {
        this.context = context;
        this.songView = songView;
    }

    public void getHitSong(String token){
        SongInterface api = RetrofitClient.getRetrofit().create(SongInterface.class);
        Call<SongResponse> songResponseCall = api.getSong(token);

        songResponseCall.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if(response.body().getStatus() == 200){
                    songView.songList(response.body().getSongList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAlbumSong(String token, String album_id){
        SongInterface api = RetrofitClient.getRetrofit().create(SongInterface.class);
        Call<SongResponse> songResponseCall = api.getAlbumSong(token, album_id);

        songResponseCall.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if(response.body().getStatus() == 200){
                    songView.songList(response.body().getSongList());
                    Log.d("Album song", "onResponse: "+ response.body().getSongList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
