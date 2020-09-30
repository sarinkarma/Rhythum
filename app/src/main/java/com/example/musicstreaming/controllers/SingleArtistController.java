package com.example.musicstreaming.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.response.ArtistResponse;
import com.example.musicstreaming.models.response.SingleArtistResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.ArtistInterface;
import com.example.musicstreaming.views.SingleArtistView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleArtistController {
    Context context;
    SingleArtistView singleArtistView;

    public SingleArtistController(Context context, SingleArtistView singleArtistView) {
        this.context = context;
        this.singleArtistView = singleArtistView;
    }

    public void getSingleArtist(String token, String artist_id){
        ArtistInterface api = RetrofitClient.getRetrofit().create(ArtistInterface.class);
        Call<SingleArtistResponse> artistResponseCall = api.getSingleArtist(token, artist_id);

        artistResponseCall.enqueue(new Callback<SingleArtistResponse>() {
            @Override
            public void onResponse(Call<SingleArtistResponse> call, Response<SingleArtistResponse> response) {
                Log.d("Artist", "onResponse: "+ response);
                if(response.body().getStatus() == 200){
                    singleArtistView.artist(response.body().getArtist());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleArtistResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
