package com.example.spotify.model.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {

    int id;
    boolean like;

    boolean selected;

    @SerializedName("name")
    @Expose
    String name;


    String time;


    @SerializedName("duration")
    @Expose
    private Long time_original;


    public Song(int id, boolean like, String name, String time) {
        this.id = id;
        this.like = like;
        this.name = name;
        this.time = time;
    }

    //#region Getters i Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Long getTime_original() {
        return time_original;
    }

    public void setTime_original(Long time_original) {
        this.time_original = time_original;
    }


    //#endregion
}
