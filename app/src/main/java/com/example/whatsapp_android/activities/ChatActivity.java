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
import com.example.whatsapp_android.entities.Transfer;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.example.whatsapp_android.viewmodels.MessagesViewModel;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private PreferenceManager preferenceManager;
    private MessagesViewModel messagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        loadReceivedDetails();

        MessageAdapter messageAdapter = new MessageAdapter(null);
        binding.chatRecyclerView.setAdapter(messageAdapter);

        messagesViewModel = new MessagesViewModel(preferenceManager);
        messagesViewModel.get().observe(this, messages -> {
            loading(true);

            binding.chatRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

            messageAdapter.setMessages(messages);
            messageAdapter.notifyItemRangeChanged(0,messageAdapter.getItemCount());
            binding.chatRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount());

            loading(false);
        });

        setListeners();
    }


    private void sendMessage() {
        Transfer transfer = new Transfer(preferenceManager.getString(Constants.KEY_USERNAME),
                                        preferenceManager.getString(Constants.KEY_CONTACT),
                                        binding.inputMessage.getText().toString());
        binding.inputMessage.setText(null);
        messagesViewModel.add(transfer);
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }


    private void loadReceivedDetails() {
        Contact receiverContact = (Contact) getIntent().getSerializableExtra(Constants.KEY_CONTACT);
        binding.textName.setText(receiverContact.getName());
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
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