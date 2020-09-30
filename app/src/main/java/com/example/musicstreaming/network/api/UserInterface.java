package com.example.musicstreaming.network.api;

import com.example.musicstreaming.models.request.ProfileRequest;
import com.example.musicstreaming.models.response.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserInterface {

    @Multipart
    @PATCH("user/{user_id}/image")
    Call<UserResponse> uploadImage(@Header("Authorization") String token, @Path("user_id") String user_id, @Part MultipartBody.Part image);

    @PATCH("user/{user_id}")
    Call<UserResponse> updateProfile(@Header("Authorization") String token, @Path("user_id") String user_id, @Body ProfileRequest profileRequest);

}
