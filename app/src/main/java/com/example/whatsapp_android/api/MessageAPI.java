package com.example.whatsapp_android.api;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import com.example.whatsapp_android.Dao.MessageDao;
import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.entities.Transfer;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {

    WebServiceAPI webServiceAPI;
    MessageDao messageDao;
    PreferenceManager preferenceManager;

    public MessageAPI(MessageDao messageDao, PreferenceManager preferenceManager) {
        this.messageDao = messageDao;
        this.preferenceManager = preferenceManager;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(preferenceManager.getString(Constants.KEY_SERVER))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getAllMessages(MutableLiveData<List<Message>> messages, String receiverId) {
        String token = "Bearer " + preferenceManager.getString(Constants.KEY_TOKEN);
        Call<List<Message>> call = webServiceAPI.getAllMessage(token, receiverId);
        String id = preferenceManager.getString(Constants.KEY_USERNAME);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if(response.code() == 200) {
                    messageDao.clear(id, receiverId);
                    if(response.body() != null){
                        List<Message> result = response.body();
                        for (Message message: result) {
                            message.setUserId(id);
                            message.setReceiverId(receiverId);
                            messageDao.insert(message);
                        }
                        messages.setValue(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {      }
        });
    }


    public void addNewMessage(MutableLiveData<List<Message>> messages, String receiverId, Message message) {
        String token = "Bearer " + preferenceManager.getString(Constants.KEY_TOKEN);
        Call<String> call = webServiceAPI.addNewMessage(token, receiverId, message);

        call.enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 201) {
                    String t = LocalDateTime.now().toString();
                    message.setCreated(t);
                    message.setUserId(preferenceManager.getString(Constants.KEY_USERNAME));
                    message.setReceiverId(preferenceManager.getString(Constants.KEY_CONTACT));

                    messageDao.insert(message);
                    messages.setValue(messageDao.index(preferenceManager.getString(Constants.KEY_USERNAME),
                            preferenceManager.getString(Constants.KEY_CONTACT)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {           }
        });

    }

    public void sendMessage(MutableLiveData<List<Message>> messages, Transfer transfer) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(preferenceManager.getString(Constants.KEY_CONTACT_SERVER))
                .build();
        WebServiceAPI receiverSide = retrofit.create(WebServiceAPI.class);

        Call<String> call = receiverSide.sendMessage(transfer);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 201) {
                    Message message = new Message(0, transfer.getContent(), null, true,null,null);
                    addNewMessage(messages, transfer.getTo(), message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {      }
        });
    }
}
