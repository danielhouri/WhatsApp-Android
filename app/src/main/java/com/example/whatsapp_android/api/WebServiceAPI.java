package com.example.whatsapp_android.api;

import com.example.whatsapp_android.entities.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface WebServiceAPI {
    @POST("Users/signin")
    Call<String> signIn(@Body User user);

    @POST("Users/signup")
    Call<String> signUp(@Body User user);
}
