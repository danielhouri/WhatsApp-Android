package com.example.whatsapp_android.entities;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface MessageDao {
    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
