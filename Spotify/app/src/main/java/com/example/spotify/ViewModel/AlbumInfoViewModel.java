package com.example.spotify.ViewModel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.spotify.DataBase.AlbumDao;
import com.example.spotify.DataBase.AppDatabase;
import com.example.spotify.model.classes.Album;
import com.example.spotify.model.formatters.Converter;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AlbumInfoViewModel extends AndroidViewModel {

    private AppDatabase db;
    private final MutableLiveData<List<Album>> llistaAlbums= new MutableLiveData<>();

    public AlbumInfoViewModel(@NonNull Application application) {
        super(application);
        db= Room.databaseBuilder(application, AppDatabase.class, "db-albums.db").build();

    }

    public void setLlista(List<Album> entrada){
        llistaAlbums.setValue(entrada);
    }

    public LiveData<List<Album>> getLlistaAlbums(){
        return llistaAlbums;
    }


    public void insert(Album entrada){

        Observable.fromCallable(()->{

            AlbumDao albumDao= db.albumDao();
            entrada.setFromBitmap(Converter.fromBitmap(entrada.getImageBitmap()));

            if(entrada.getDate()==null){
                entrada.setTimeStamp(0);
            }else{
                entrada.setTimeStamp(Converter.dateToTimestamp(entrada.getDate()));
            }


            albumDao.insertAll(entrada);


            return true;
        }).subscribeOn(Schedulers.io()).subscribe();

    }




}
