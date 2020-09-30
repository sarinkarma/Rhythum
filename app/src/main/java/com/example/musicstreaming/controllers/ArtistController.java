package com.example.musicstreaming.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.Artist;
import com.example.musicstreaming.models.response.ArtistResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.ArtistInterface;
import com.example.musicstreaming.views.GetArtistView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistController {
    Context context;
    GetArtistView getArtistView;

    public ArtistController(Context context, GetArtistView getArtistView) {
        this.context = context;
        this.getArtistView = getArtistView;
    }

    public void getArtist(String token){
        Log.d("artist", token);
        ArtistInterface api = RetrofitClient.getRetrofit().create(ArtistInterface.class);
        Call<ArtistResponse> artistResponseCall = api.getArtist(token);

        artistResponseCall.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                Log.d("artist", response.body().getMessage());
                if(response.body().getStatus() == 200){
                    getArtistView.artistList(response.body().getArtistList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtistResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
