package com.example.spotify.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.spotify.model.classes.Album;

@Database(entities = {Album.class}, version = 3)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
}
