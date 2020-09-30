package com.example.musicstreaming.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.musicstreaming.models.request.ProfileRequest;
import com.example.musicstreaming.models.response.UserResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.UserInterface;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.ProfileUpdateView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdateController {
    Context context;
    ProfileUpdateView profileUpdateView;
    SharedPreferences sharedPreferences;

    public ProfileUpdateController(Context context, ProfileUpdateView profileUpdateView, SharedPreferences sharedPreferences) {
        this.context = context;
        this.profileUpdateView = profileUpdateView;
        this.sharedPreferences = sharedPreferences;
    }

    public void updateProfile(String token, String user_id, String username, String dob,  String gender){
        UserInterface api = RetrofitClient.getRetrofit().create(UserInterface.class);
        Call<UserResponse> userResponseCall = api.updateProfile(token, user_id, new ProfileRequest(username, gender, dob));

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getStatus() == 200){
                    editor.putString(Constants.Name, response.body().getUser().getUsername());

                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getUser());
                    editor.putString(Constants.USER, json);

                    editor.commit();

                    Toast.makeText(context, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                    profileUpdateView.isUpdate(true);
                }else{
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                    profileUpdateView.isUpdate(false);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                profileUpdateView.isUpdate(false);
            }
        });
    }
}
