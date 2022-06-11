package com.example.whatsapp_android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.whatsapp_android.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message WHERE userId=:userId and receiverId=:receiverId")
    List<Message> index(String userId, String receiverId);

    @Query("SELECT * FROM message WHERE userid=:userId and id=:id and receiverId=:receiverId")
    Message get(int id, String userId, String receiverId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM message Where userId=:userId and receiverId=:receiverId")
    void clear(String userId, String receiverId);
}
