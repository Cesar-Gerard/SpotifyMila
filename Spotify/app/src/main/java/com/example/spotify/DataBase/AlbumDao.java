package com.example.spotify.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spotify.model.classes.Album;
import com.example.spotify.model.classes.Song;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album")
    LiveData<List<Album>> getAll();

    @Query("SELECT * FROM song WHERE albumId = :entrada")
    LiveData<List<Song>> getSongByAlbum(long entrada);


    @Insert
    long insert(Album album);


    @Insert
    long insertSong(Song song);

    @Update
    void update(Album a);

    @Delete
    void delete(Album album);


    @Delete
    void deleteSong(Song entrada);
}
