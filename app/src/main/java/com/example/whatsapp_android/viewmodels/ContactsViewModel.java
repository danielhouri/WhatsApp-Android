package com.example.whatsapp_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.repositories.ContactsRepository;
import com.example.whatsapp_android.utilities.PreferenceManager;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private ContactsRepository repository;
    private final LiveData<List<Contact>> contacts;

    public ContactsViewModel(PreferenceManager preferenceManager) {
        this.repository = new ContactsRepository(preferenceManager);
        contacts = repository.getAll();
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public void add(Contact contact) {repository.add(contact);}

}
