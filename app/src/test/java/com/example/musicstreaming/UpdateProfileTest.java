package com.example.musicstreaming;

import com.example.musicstreaming.models.request.ProfileRequest;
import com.example.musicstreaming.models.response.UserResponse;
import com.example.musicstreaming.network.RetrofitClient;
import com.example.musicstreaming.network.api.UserInterface;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class UpdateProfileTest {
    UserInterface userInterface = RetrofitClient.getRetrofit().create(UserInterface.class);

    @Test
    public void updateProfileTest(){
        Call<UserResponse> userResponseCall = userInterface.updateProfile("null","1",
                new ProfileRequest("sarinkarma","male","25/12/1997"));
        try{
            Response<UserResponse> userResponse = userResponseCall.execute();
            assertTrue(userResponse.isSuccessful());
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
