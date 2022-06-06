package com.example.whatsapp_android.api;

import com.example.whatsapp_android.R;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.utilities.WhatsApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
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

    public Call<String> signUp(User user) {
        return webServiceAPI.signUp(user);
    }

    public Call<String> signIn(User user) {
        return webServiceAPI.signIn(user);
    }
}
