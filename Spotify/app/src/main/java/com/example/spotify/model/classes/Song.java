package com.example.spotify.model.classes;

public class Song {

    int id;
    boolean like;

    boolean selected;

    String name;

   String time;

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

    //#endregion
}
