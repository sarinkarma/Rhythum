package com.example.musicstreaming.controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.musicstreaming.models.response.GenreResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.GenreInterface;
import com.example.musicstreaming.views.GenreView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreController {
    Context context;
    GenreView genreView;

    public GenreController(Context context, GenreView genreView) {
        this.context = context;
        this.genreView = genreView;
    }

    public void getGenre(String token){
        GenreInterface api = RetrofitClient.getRetrofit().create(GenreInterface.class);
        Call<GenreResponse> genreResponseCall = api.getGenre(token);

        genreResponseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if(response.body().getStatus() == 200){
                    genreView.genreList(response.body().getGenreList());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
