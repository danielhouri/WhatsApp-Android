package com.example.whatsapp_android.localdatabse;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.whatsapp_android.Dao.ContactDao;
import com.example.whatsapp_android.Dao.MessageDao;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
