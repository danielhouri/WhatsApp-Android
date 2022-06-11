package com.example.whatsapp_android.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.entities.Transfer;
import com.example.whatsapp_android.repositories.MessagesRepository;
import com.example.whatsapp_android.utilities.PreferenceManager;

import java.util.List;

public class MessagesViewModel {
    private final MessagesRepository repository;
    private final LiveData<List<Message>> messages;

    public MessagesViewModel(PreferenceManager preferenceManager) {
        this.repository = new MessagesRepository(preferenceManager);
        messages = repository.getAll();
    }

    public LiveData<List<Message>> get() {return messages;}

    public void add(Transfer transfer) {repository.add(transfer);}
}
