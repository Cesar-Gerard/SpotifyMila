package model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.classes.Album;

public class AlbumInfoViewModel extends ViewModel {

    private final MutableLiveData<List<Album>> llistaAlbums= new MutableLiveData<>();

    public void setLlista(List<Album> entrada){
        llistaAlbums.setValue(entrada);
    }

    public LiveData<List<Album>> getLlistaAlbums(){
        return llistaAlbums;
    }

}
