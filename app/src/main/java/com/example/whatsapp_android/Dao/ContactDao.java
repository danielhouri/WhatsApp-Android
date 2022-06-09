package com.example.whatsapp_android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatsapp_android.entities.Contact;

import java.util.List;


@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact WHERE userid=:userId")
    List<Contact> index(String userId);

    @Query("SELECT * FROM contact WHERE userid=:userId and id=:id")
    Contact get(String userId, String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);

    @Query("DELETE FROM contact Where userid=:userId")
    void clear(String userId);
}
