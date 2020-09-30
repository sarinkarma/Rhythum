package com.example.musicstreaming.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.musicstreaming.models.request.LoginRequest;
import com.example.musicstreaming.models.response.LoginResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AuthInterface;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.Authentication;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    Context context;
    SharedPreferences sharedPreferences;
    public boolean isAuth = false;
    Authentication authentication;

    public LoginController(Context context, SharedPreferences sharedPreferences, Authentication authentication) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.authentication = authentication;
    }

    public void login(String email, String password) {
        AuthInterface api = RetrofitClient.getRetrofit().create(AuthInterface.class);
        Call<LoginResponse> loginResponseCall = api.login(new LoginRequest(email, password));

        final SharedPreferences.Editor editor =sharedPreferences.edit();

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 200){
                    editor.putString(Constants.TOKEN, response.body().getToken());
                    editor.putString(Constants.EMAIL, response.body().getUser().getEmail());
                    editor.putString(Constants.Name, response.body().getUser().getUsername());
                    editor.putString(Constants.USER_IMAGE, response.body().getUser().getImage());

                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getUser());
                    editor.putString(Constants.USER, json);

                    editor.commit();

                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                    authentication.isAuthenticated(true);
                }else{
                    authentication.isAuthenticated(false);
                    Toast.makeText(context, "Login Failed!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                authentication.isAuthenticated(false);
            }
        });
    }
}
