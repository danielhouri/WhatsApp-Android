package com.example.whatsapp_android.api;

import androidx.annotation.NonNull;

import com.example.whatsapp_android.R;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.utilities.WhatsApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WhatsAppAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public WhatsAppAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(WhatsApp.context.getString(R.string.BaseUrl))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    /**
     * This function built the call context to made the signup
     * @param user the user credentials
     * @return call context
     */
    public Call<String> signUp(User user) {
        return webServiceAPI.signUp(user);
    }


    /**
     * This function built the call context to made the signin
     * @param user the user credentials
     * @return call context
     */
    public Call<JsonObject> signIn(User user) {
        return webServiceAPI.signIn(user);
    }

    public void sendToken(String username, String token) {
        User user = new User(username, token, "null","null");

        Call<String> call = webServiceAPI.sendFirebase(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {            }
        });
    }
}
