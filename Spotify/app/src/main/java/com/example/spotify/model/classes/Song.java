package com.example.spotify.model.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Song {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "albumId")
    long album_id;

    @ColumnInfo(name = "fav")
    boolean like;

    @Ignore
    boolean selected;

    @ColumnInfo(name = "songName")
    @SerializedName("name")
    @Expose
    String name;


    @ColumnInfo(name = "songDuration")
    String time;



    @SerializedName("duration")
    @Expose
    @Ignore
    private Integer time_original;




    public Song(long id, long album_id, boolean like, String name, String time) {
        this.id = id;
        this.album_id = album_id;
        this.like = like;
        this.name = name;
        this.time = time;
    }


    //#region Getters i Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getTime_original() {
        return time_original;
    }

    public void setTime_original(Integer time_original) {
        this.time_original = time_original;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public void convertTime(Integer timeOriginal) {

        if(timeOriginal!=null) {
            int minutos = timeOriginal / 60;
            int segundos = timeOriginal % 60;
            this.time = minutos + ":" + segundos;
        }else{
            this.time = "00" + ":" + "00";
        }
    }

    //#endregion
}
