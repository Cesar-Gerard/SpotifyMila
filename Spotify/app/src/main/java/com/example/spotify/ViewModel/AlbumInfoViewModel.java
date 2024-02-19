package com.example.spotify.ViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.spotify.DataBase.AlbumDao;
import com.example.spotify.DataBase.AppDatabase;
import com.example.spotify.model.classes.Album;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AlbumInfoViewModel extends AndroidViewModel {

    private AppDatabase db;
    public MutableLiveData<Boolean> insertFet = new MutableLiveData<>();

    private final MutableLiveData<List<Album>> llistaAlbums= new MutableLiveData<>();

    public void setLlista(List<Album> entrada){
        llistaAlbums.setValue(entrada);
    }

    public LiveData<List<Album>> getLlistaAlbums(){
        return llistaAlbums;
    }


    public AlbumInfoViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application,
                AppDatabase.class, "db_persones.db").build();
        insertFet.setValue(false);
    }


    public LiveData<List<Album>> getAlbums(){
        AlbumDao userDao = db.albumDao();
        return  userDao.getAll();
    }


    public void insert(Album u) {

        Observable.fromCallable(() -> {

            AlbumDao userDao = db.albumDao();
            u.setId(userDao.insert(u));
            insertFet.postValue(true);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();


    }


    public void delete(Album u){
        Observable.fromCallable(() -> {
            AlbumDao userDao = db.albumDao();
            userDao.delete(u);
            llistaAlbums.getValue().remove(u);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    public void update(Album u){
        Observable.fromCallable(() -> {
            AlbumDao userDao = db.albumDao();
            userDao.update(u);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }


}
