package com.example.whatsapp_android.LocalDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatsapp_android.entities.Message;
import com.example.whatsapp_android.entities.MessageDao;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.entities.UserDao;

@Database(entities = {User.class, Message.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
}
