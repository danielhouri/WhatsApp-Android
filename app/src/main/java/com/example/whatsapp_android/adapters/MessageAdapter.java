package com.example.whatsapp_android.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_android.databinding.ItemContainerRecivedMessageBinding;
import com.example.whatsapp_android.databinding.ItenContainerSentMessageBinding;
import com.example.whatsapp_android.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Message> messages;
    private final Bitmap receiverProfileImage;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public MessageAdapter(Bitmap receiverProfileImage) {
        this.messages = new ArrayList<>();
        this.receiverProfileImage = receiverProfileImage;
    }

    /**
     * Set the array with the updated messages list.
     * @param messages the new list
     */
    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
    }

    /**
     * Check the message side and show it to the screen
     * @param parent viewgroup
     * @param viewType the side of the message
     * @return the messageView with the data
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT) {
            return new SendMessageViewHolder(ItenContainerSentMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
        }
        else {
            return new ReceivedMessageViewHolder(ItemContainerRecivedMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SendMessageViewHolder) holder).setData(messages.get(position));
        }
        else {
            ((ReceivedMessageViewHolder) holder).setData(messages.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isSend()) {
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SendMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItenContainerSentMessageBinding binding;

        SendMessageViewHolder(ItenContainerSentMessageBinding itenContainerSentMessageBinding) {
            super(itenContainerSentMessageBinding.getRoot());
            binding = itenContainerSentMessageBinding;
        }

        void setData(Message message) {
            binding.textMessage.setText(message.getContent());
            binding.textDateTime.setText(message.getCreated());
        }
    }

    /**
     * Set the message params
     */
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerRecivedMessageBinding binding;

        ReceivedMessageViewHolder(ItemContainerRecivedMessageBinding itemContainerRecivedMessageBinding) {
            super(itemContainerRecivedMessageBinding.getRoot());
            binding = itemContainerRecivedMessageBinding;
        }

        void setData(Message message, Bitmap receiverProfileImage) {
            binding.textMessage.setText(message.getContent());
            binding.textDateTime.setText(message.getCreated());
            //binding.imageProfile.setImageBitmap(receiverProfileImage);
        }
    }
}
