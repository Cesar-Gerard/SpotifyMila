package com.example.spotify.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.spotify.modelApi.TopAlbums.Album;

import java.util.List;
import java.util.Map;

public class InsideAlbumViewModel extends ViewModel {

    private final MutableLiveData< List<Album>> llistaAlbums= new MutableLiveData<>();

    public void setLlista(List<Album> entrada){
        llistaAlbums.setValue(entrada);
    }

    public LiveData<List<Album>> getLlistaAlbums(){
        return llistaAlbums;
    }


}
