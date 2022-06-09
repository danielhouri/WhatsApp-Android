package com.example.whatsapp_android.api;

import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Invitation;
import com.example.whatsapp_android.entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface WebServiceAPI {
    @POST("api/Users/signin")
    Call<JsonObject> signIn(@Body User user);

    @POST("api/Users/signup")
    Call<String> signUp(@Body User user);

    @GET("api/Contacts")
    Call<List<Contact>> getAllContacts(@Header("Authorization") String token);

    @POST("api/Contacts")
    Call<String> addNewContact(@Header("Authorization") String token, @Body Contact contact);

    @POST("api/Invitations")
    Call<String> sendInvitation(@Body Invitation invitation);
}
