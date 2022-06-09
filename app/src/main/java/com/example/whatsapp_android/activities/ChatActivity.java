package com.example.whatsapp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.example.whatsapp_android.adapters.MessageAdapter;
import com.example.whatsapp_android.databinding.ActivityChatBinding;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Contact receiverContact;
    private List<Message> messages;
    private MessageAdapter messageAdapter;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceivedDetails();
        init();
        getMessages();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(
                messages,
                null,
                preferenceManager.getString(Constants.KEY_USERNAME)
        );
        binding.chatRecyclerView.setAdapter(messageAdapter);
    }

    private void getMessages() {
        //Add new
        Message newMessage = new Message(0,"ron", "Hello, how are you?", true, new Date());
        Message newMessage1 = new Message(1,"ron", "Are you there?", false, new Date());
        Message newMessage2 = new Message(2,"ron", "Hello?", true, new Date());
        Message newMessage3 = new Message(3,"ron", "Hi", false, new Date());
        Message newMessage4 = new Message(4,"ron", "Miao", true, new Date());
        Message newMessage5 = new Message(5,"ron", "Hamin", false, new Date());

        messages.add(newMessage);
        messages.add(newMessage1);
        messages.add(newMessage2);
        messages.add(newMessage3);
        messages.add(newMessage4);
        messages.add(newMessage5);

        // Display
        //Collections.sort(messages, (obj1, obj2) -> obj1.getCreated().compareTo(obj2.getCreated()));
        //messageAdapter.notifyDataSetChanged();
        binding.chatRecyclerView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);

    }

    private void sendMessage() {
        Message newMessage = new Message(0,preferenceManager.getString(Constants.KEY_CONTACT),
                binding.inputMessage.getText().toString(),
                true,
                new Date());
        messages.add(newMessage);
        binding.inputMessage.setText(null);
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }


    private void loadReceivedDetails() {
        receiverContact = (Contact) getIntent().getSerializableExtra(Constants.KEY_CONTACT);
        binding.textName.setText(receiverContact.getName());

    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
}