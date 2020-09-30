package com.example.musicstreaming.controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.musicstreaming.models.response.SingleSongResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.SongInterface;
import com.example.musicstreaming.views.SingleSongView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleSongController {
    Context context;
    SingleSongView singleSongView;

    public SingleSongController(Context context, SingleSongView singleSongView) {
        this.context = context;
        this.singleSongView = singleSongView;
    }

    public void getSingleSong(String token, String song_id){
        SongInterface api = RetrofitClient.getRetrofit().create(SongInterface.class);
        Call<SingleSongResponse> songResponseCall = api.getSingleSong(token, song_id);

        songResponseCall.enqueue(new Callback<SingleSongResponse>() {
            @Override
            public void onResponse(Call<SingleSongResponse> call, Response<SingleSongResponse> response) {
                if(response.body().getStatus() == 200){
                    singleSongView.song(response.body().getSong());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleSongResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
