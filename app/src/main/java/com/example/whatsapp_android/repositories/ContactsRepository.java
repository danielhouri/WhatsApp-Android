package com.example.whatsapp_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsapp_android.Dao.ContactDao;
import com.example.whatsapp_android.R;
import com.example.whatsapp_android.api.ContactAPI;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Invitation;
import com.example.whatsapp_android.localdatabse.LocalDatabase;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.example.whatsapp_android.utilities.WhatsApp;
import java.util.List;


public class ContactsRepository {

    private final ContactDao contactDao;
    private final ContactListData contactListData;
    private final PreferenceManager preferenceManager;
    private final ContactAPI contactAPI;

    public ContactsRepository(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        contactDao = Room.databaseBuilder(WhatsApp.context, LocalDatabase.class, "AppDB1")
                    .allowMainThreadQueries().build().contactDao();
        contactListData = new ContactListData();
        this.contactAPI = new ContactAPI(contactDao, preferenceManager);

    }

    public LiveData<List<Contact>> getAll(){
        return contactListData;
    }

    public void add(final Contact contact) {
        final Invitation invitation = new Invitation(preferenceManager.getString(Constants.KEY_USERNAME),
                                contact.getId(),
                                WhatsApp.context.getString(R.string.BaseUrl));
        contactAPI.sendInvitation(contactListData, contact, invitation);
    }

    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
                    contactListData
                            .postValue(contactDao.index(preferenceManager
                                    .getString(Constants.KEY_USERNAME))))
                    .start();
            contactAPI.getAllContact(this);
        }
    }
}
