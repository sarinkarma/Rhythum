package com.example.musicstreaming.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.request.SearchRequest;
import com.example.musicstreaming.models.response.SearchResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.SearchInterface;
import com.example.musicstreaming.views.SearchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchController {
    Context context;
    SearchView searchView;

    public SearchController(Context context, SearchView searchView) {
        this.context = context;
        this.searchView = searchView;
    }

    public void search(String token, String search){
        SearchInterface api = RetrofitClient.getRetrofit().create(SearchInterface.class);
        Call<SearchResponse> searchResponseCall = api.search(token, new SearchRequest(search));

        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.d("searchResult", "onResponse: "+ response.body().getSearch().getSongs());
                if(response.body().getStatus() == 200){
                    searchView.search(response.body().getSearch());
                }else{
                    Toast.makeText(context, "Error" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
