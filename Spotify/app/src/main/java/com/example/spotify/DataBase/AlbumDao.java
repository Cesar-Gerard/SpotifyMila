package com.example.spotify.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.spotify.model.classes.Album;

@Dao
public interface AlbumDao {
    @Insert
    public void insertAll(Album... albums);


}
