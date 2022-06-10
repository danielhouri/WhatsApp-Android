package com.example.whatsapp_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsapp_android.Dao.MessageDao;
import com.example.whatsapp_android.api.MessageAPI;
import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.entities.Transfer;
import com.example.whatsapp_android.localdatabse.LocalDatabase;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.example.whatsapp_android.utilities.WhatsApp;

import java.util.List;

public class MessagesRepository {

    private final MessageDao messageDao;
    private final MessagesRepository.MessageListData messageListData;
    private final PreferenceManager preferenceManager;
    private final MessageAPI messageAPI;

    public MessagesRepository(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        messageDao = Room.databaseBuilder(WhatsApp.context, LocalDatabase.class, "AppDB60")
                .allowMainThreadQueries().build().messageDao();
        messageListData = new MessageListData();
        this.messageAPI = new MessageAPI(messageDao, preferenceManager);
    }

    public LiveData<List<Message>> getAll(){
        return messageListData;
    }

    public void add(final Transfer transfer) {
        messageAPI.sendMessage(messageListData, transfer, "http://10.0.2.2:5156/");
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
        }

        @Override
        protected void onActive() {
            messageListData.postValue(messageDao.index(preferenceManager.getString(Constants.KEY_USERNAME),
                    preferenceManager.getString(Constants.KEY_CONTACT)));
            super.onActive();
            new Thread(() -> messageAPI.getAllMessages(this,preferenceManager.getString(Constants.KEY_CONTACT))).start();
        }
    }
}
