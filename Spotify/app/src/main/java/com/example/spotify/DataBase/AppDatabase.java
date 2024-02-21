package com.example.spotify.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.spotify.model.classes.Album;
import com.example.spotify.model.classes.Song;

@Database(entities = {Album.class, Song.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
}
