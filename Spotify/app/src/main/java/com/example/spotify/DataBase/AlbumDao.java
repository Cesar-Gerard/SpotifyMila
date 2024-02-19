package com.example.spotify.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spotify.model.classes.Album;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album")
    LiveData<List<Album>> getAll();

    @Insert
    long insert(Album album);

    @Update
    void update(Album a);

    @Delete
    void delete(Album album);


}
