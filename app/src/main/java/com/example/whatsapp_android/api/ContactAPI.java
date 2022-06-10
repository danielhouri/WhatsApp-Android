package com.example.whatsapp_android.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.whatsapp_android.Dao.ContactDao;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Invitation;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {
    WebServiceAPI webServiceAPI;
    ContactDao contactDao;
    PreferenceManager preferenceManager;

    public ContactAPI(ContactDao contactDao, PreferenceManager preferenceManager) {
        this.contactDao = contactDao;
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

    public void getAllContact(MutableLiveData<List<Contact>> contacts) {
        String token = "Bearer " + preferenceManager.getString(Constants.KEY_TOKEN);
        Call<List<Contact>> call = webServiceAPI.getAllContacts(token);

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                if(response.code() == 200) {
                    contactDao.clear(preferenceManager.getString(Constants.KEY_USERNAME));
                    if(response.body() != null){
                        List<Contact> result = response.body();
                        for (Contact contact: result) {
                            contact.setUserid(preferenceManager.getString(Constants.KEY_USERNAME));
                            contactDao.insert(contact);
                        }
                        contacts.setValue(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {            }
        });
    }

    public void addNewContact(MutableLiveData<List<Contact>> contacts, Contact contact) {
        String token = "Bearer " + preferenceManager.getString(Constants.KEY_TOKEN);
        Call<String> call = webServiceAPI.addNewContact(token, contact);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 201) {
                    contact.setUserid(preferenceManager.getString(Constants.KEY_USERNAME));
                    contactDao.insert(contact);
                }
                contacts.setValue(contactDao.index("daniel"));
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {            }
        });
    }

    public void sendInvitation(MutableLiveData<List<Contact>> contacts, Contact contact, Invitation invitation) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(contact.getServer())
                .build();
        WebServiceAPI receiverSide = retrofit.create(WebServiceAPI.class);

        Call<String> call = receiverSide.sendInvitation(invitation);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 201) {
                    addNewContact(contacts, contact);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {            }
        });
    }
}
