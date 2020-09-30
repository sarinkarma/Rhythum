package com.example.musicstreaming.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.musicstreaming.models.response.LogoutResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.AuthInterface;
import com.example.musicstreaming.utils.Constants;
import com.example.musicstreaming.views.LogoutView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutController {
    Context context;
    SharedPreferences sharedPreferences;
    LogoutView logoutView;

    public LogoutController(Context context, SharedPreferences sharedPreferences, LogoutView logoutView) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.logoutView = logoutView;
    }

    public void logout(String token) {
        AuthInterface api = RetrofitClient.getRetrofit().create(AuthInterface.class);
        Call<LogoutResponse> logoutResponseCall = api.logout(token);

        final SharedPreferences.Editor editor =sharedPreferences.edit();

        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                Log.d("test", "onResponse: "+ response.body());
                if(response.body().getStatus() == 200){
                    editor.putString(Constants.TOKEN, "");
                    editor.putString(Constants.EMAIL, "");
                    editor.putString(Constants.Name, "");
                    editor.putString(Constants.USER_IMAGE, "");
                    editor.putString(Constants.USER, "");

                    editor.commit();
                    logoutView.isLoggedOut(true);
                }else{
                    logoutView.isLoggedOut(false);
                    Toast.makeText(context, "Logout Failed!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                logoutView.isLoggedOut(false);
                Toast.makeText(context, "Logout Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
