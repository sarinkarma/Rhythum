package com.example.musicstreaming.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.response.UserResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.UserInterface;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.UserImageView;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadController {
    Context context;
    UserImageView userImageView;
    SharedPreferences sharedPreferences;

    public ImageUploadController(Context context, SharedPreferences sharedPreferences, UserImageView userImageView) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.userImageView = userImageView;
    }

    public void uploadImage(String token, String user_id, MultipartBody.Part image){
        UserInterface api = RetrofitClient.getRetrofit().create(UserInterface.class);
        Call<UserResponse> userResponseCall = api.uploadImage(token, user_id, image);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("imageUpdate", "onResponse: "+ response.body());
                if(response.body().getStatus() == 200){
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getUser());
                    editor.putString(Constants.USER, json);
                    editor.commit();

                    userImageView.isAdded(true);
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    userImageView.isAdded(false);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("imageUpdate", "onFailure: "+ t.getMessage());
                userImageView.isAdded(false);
            }
        });
    }
}
