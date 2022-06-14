package com.example.whatsapp_android.api;

import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Invitation;
import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.entities.Transfer;
import com.example.whatsapp_android.entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("api/Users/signin")
    Call<JsonObject> signIn(@Body User user);

    @POST("api/Users/signup")
    Call<String> signUp(@Body User user);

    @POST("api/Users/firebase")
    Call<String> sendFirebase(@Body User user);

    @GET("api/Contacts")
    Call<List<Contact>> getAllContacts(@Header("Authorization") String token);

    @POST("api/Contacts")
    Call<String> addNewContact(@Header("Authorization") String token, @Body Contact contact);

    @POST("api/Invitations")
    Call<String> sendInvitation(@Body Invitation invitation);

    @GET("api/Contacts/{id}/messages")
    Call<List<Message>> getAllMessage(@Header("Authorization") String token, @Path("id") String receiverId);

    @POST("api/Contacts/{id}/messages")
    Call<String> addNewMessage(@Header("Authorization") String token, @Path("id") String receiverId, @Body Message message);

    @POST("api/Transfer")
    Call<String> sendMessage(@Body Transfer transfer);
}
