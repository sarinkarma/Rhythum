package com.example.musicstreaming.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.request.SignUpRequest;
import com.example.musicstreaming.models.response.SignUpReponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AuthInterface;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.Authentication;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpController {
    Context context;
    SharedPreferences sharedPreferences;
    public boolean isAuth = false;
    Authentication authentication;

    public SignUpController(Context context, SharedPreferences sharedPreferences, Authentication authentication) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.authentication = authentication;
    }

    public void signUp(String email, String password, String username, String gender, String dob, String account){
        AuthInterface api = RetrofitClient.getRetrofit().create(AuthInterface.class);
        Call<SignUpReponse> signUpReponseCall = api.signup(new SignUpRequest(email, password, username, gender, dob, account));

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        signUpReponseCall.enqueue(new Callback<SignUpReponse>() {
            @Override
            public void onResponse(Call<SignUpReponse> call, Response<SignUpReponse> response) {
                if (response.body().getStatus() == 200){
                    editor.putString(Constants.TOKEN, response.body().getToken());
                    editor.putString(Constants.EMAIL, response.body().getUser().getEmail());
                    editor.putString(Constants.Name, response.body().getUser().getUsername());

                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getUser());
                    editor.putString(Constants.USER, json);

                    editor.commit();

                    Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    authentication.isAuthenticated(true);
                }else {
                    authentication.isAuthenticated(false);
                    Toast.makeText(context, "Sign Up Failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpReponse> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                authentication.isAuthenticated(false);
            }
        });
    }
}
