package com.example.whatsapp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp_android.adapters.ContactsAdapter;
import com.example.whatsapp_android.databinding.ActivityMainBinding;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.listeners.ContactListener;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ContactListener {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private List<Contact> contacts;
    private ContactsAdapter contactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        loadUserDetails();
        init();
        setListeners();
        getContacts();
    }

    private void init() {
        contacts = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(contacts, this);
        binding.conversationsRecyclerView.setAdapter(contactsAdapter);
        //Get from db
    }

    private void getContacts() {
        loading(true);
        //Add
        Date time = new Date();

        Contact contact = new Contact("daniel", "daniel", "localserver", "many", null, time);
        Contact contact1 = new Contact("daniel1", "daniel", "localserver", "many", null, time);
        Contact contact2 = new Contact("daniel2", "daniel", "localserver", "many", null, time);
        Contact contact3 = new Contact("daniel3", "daniel", "localserver", "many", null, time);
        Contact contact4 = new Contact("daniel4", "daniel", "localserver", "many", null, time);
        Contact contact5 = new Contact("daniel5", "daniel", "localserver", "many", null, time);
        Contact contact6 = new Contact("daniel6", "daniel", "localserver", "many", null, time);
        Contact contact7 = new Contact("daniel7", "daniel", "localserver", "many", null, time);
        Contact contact8 = new Contact("daniel8", "daniel", "localserver", "many", null, time);
        Contact contact9 = new Contact("daniel9", "daniel", "localserver", "many", null, time);
        Contact contact10 = new Contact("daniel10", "daniel", "localserver", "many", null, time);

        contacts.add(contact);
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        contacts.add(contact5);
        contacts.add(contact6);
        contacts.add(contact7);
        contacts.add(contact8);
        contacts.add(contact9);
        contacts.add(contact10);
        //Change

        //Not Change

        loading(false);
        Collections.sort(contacts, (obj1, obj2) -> obj2.getLastDate().compareTo(obj1.getLastDate()));
        //contactsAdapter.notifyDataSetChanged();
        binding.conversationsRecyclerView.smoothScrollToPosition(0);
        binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);


    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v -> signOut());
    }

    private void loadUserDetails() {
        binding.textName.setText(preferenceManager.getString(Constants.KEY_NICKNAME));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        showToast("Signin out...");
        //Logout firebase
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }

    @Override
    public void onContactClicked(Contact contact) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_CONTACT, contact);
        startActivity(intent);
    }

    void loading(Boolean isLoading) {
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}