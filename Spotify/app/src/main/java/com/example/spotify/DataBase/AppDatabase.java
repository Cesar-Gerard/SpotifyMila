package com.example.spotify.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spotify.model.classes.Album;

@Database(entities = {Album.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlbumDao albumDao();

}
