package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.request.EmailCheckRequest;
import com.example.musicstreaming.models.request.LoginRequest;
import com.example.musicstreaming.models.request.SignUpRequest;
import com.example.musicstreaming.models.response.EmailCheckResponse;
import com.example.musicstreaming.models.response.LoginResponse;
import com.example.musicstreaming.models.response.LogoutResponse;
import com.example.musicstreaming.models.response.SignUpReponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthInterface {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignUpReponse> signup(@Body SignUpRequest signUpRequest);

    @POST("email_check")
    Call<EmailCheckResponse> email_check(@Body EmailCheckRequest emailCheckRequest);

    @GET("logout")
    Call<LogoutResponse> logout(@Header("Authorization") String token);
}
