package com.example.musicstreaming.controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.musicstreaming.models.response.AlbumResponse;
import com.example.musicstreaming.models.response.SingleAlbumResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AlbumInterface;
import com.example.musicstreaming.views.SingleAlbumView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleAlbumController {
    Context context;
    SingleAlbumView singleAlbumView;

    public SingleAlbumController(Context context, SingleAlbumView singleAlbumView) {
        this.context = context;
        this.singleAlbumView = singleAlbumView;
    }

    public void getAlbum(String token, String album_id){
        AlbumInterface api = RetrofitClient.getRetrofit().create(AlbumInterface.class);
        Call<SingleAlbumResponse> albumResponseCall = api.getSingleAlbum(token, album_id);

        albumResponseCall.enqueue(new Callback<SingleAlbumResponse>() {
            @Override
            public void onResponse(Call<SingleAlbumResponse> call, Response<SingleAlbumResponse> response) {
                if(response.body().getStatus() == 200){
                    singleAlbumView.album(response.body().getAlbum());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleAlbumResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
