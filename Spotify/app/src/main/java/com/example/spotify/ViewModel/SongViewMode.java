package com.example.spotify.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotify.model.classes.Album;
import com.example.spotify.model.classes.Song;

import java.util.List;

public class SongViewMode extends AndroidViewModel {
    public SongViewMode(@NonNull Application application) {
        super(application);
    }


    public long albumId;


    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public MutableLiveData<Boolean> insertFet = new MutableLiveData<>();

    private final MutableLiveData<List<Song>> llistaSongs= new MutableLiveData<>();

    public void setLlista(List<Song> entrada){
        llistaSongs.setValue(entrada);
    }

    public LiveData<List<Song>> getllistaSongs(){
        return llistaSongs;
    }






}
