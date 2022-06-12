package com.example.whatsapp_android.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_android.databinding.ItemContainerContactBinding;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.listeners.ContactListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>{

    private final List<Contact> contacts;
    ItemContainerContactBinding binding;
    private final ContactListener contactListener;


    public ContactsAdapter(ContactListener contactListener) {
        this.contactListener = contactListener;
        contacts = new ArrayList<>();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerContactBinding itemContainerUserBinding = ItemContainerContactBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ContactViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setData(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ContactViewHolder(ItemContainerContactBinding itemContainerContactBinding) {
            super(itemContainerContactBinding.getRoot());
            binding = itemContainerContactBinding;
        }

        void setData(Contact contact) {
            binding.textName.setText(contact.getName());
            binding.textLast.setText(contact.getLast());
            if(contact.getLastDate() != null) {
                binding.textTime.setText(contact.getLastDate().substring(11, 16));
            }
            //binding.imageProfile.setImageBitmap(getUserImage(contact.getImage()));
            binding.getRoot().setOnClickListener(v -> contactListener.onContactClicked(contact));
        }
    }


    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
