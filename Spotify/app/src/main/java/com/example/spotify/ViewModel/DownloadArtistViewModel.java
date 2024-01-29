package com.example.spotify.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.example.spotify.modelApi.ArtistApi.Artist;

public class DownloadArtistViewModel extends ViewModel {

    private final MutableLiveData<List<Artist>> llistaArtist= new MutableLiveData<>();

    public void setLlista(List<Artist> entrada){
        llistaArtist.setValue(entrada);
    }

    public LiveData<List<Artist>> getLlistaArtista(){
        return llistaArtist;
    }



}
