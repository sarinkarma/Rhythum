package com.example.musicstreaming;

import com.example.musicstreaming.models.response.UserResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.UserInterface;

import org.junit.Test;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class UploadImageTest {
    UserInterface userInterface = RetrofitClient.getRetrofit().create(UserInterface.class);

    @Test
    public void uploadImageTest(){
        Call<UserResponse> userResponseCall = userInterface.uploadImage("null","1",null);
        try{
            Response<UserResponse> userResponse = userResponseCall.execute();
            assertTrue(userResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
