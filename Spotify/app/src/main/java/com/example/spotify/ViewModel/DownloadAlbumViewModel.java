package com.example.spotify.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import com.example.spotify.modelApi.AlbumApi.Album;


public class DownloadAlbumViewModel extends ViewModel {


    private final MutableLiveData<Map<String, List<Album>>> llistaAlbums= new MutableLiveData<>();

    public void setLlista(Map<String, List<Album>> entrada){
        llistaAlbums.setValue(entrada);
    }

    public LiveData<Map<String, List<Album>>> getLlistaAlbums(){
        return llistaAlbums;
    }


}
