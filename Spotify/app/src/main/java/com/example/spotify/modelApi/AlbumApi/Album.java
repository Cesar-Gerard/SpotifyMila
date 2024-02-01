
package com.example.spotify.modelApi.AlbumApi;

import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import com.example.spotify.modelApi.TopAlbums.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

import com.example.spotify.Interface.AlbumInterface;

@Generated("jsonschema2pojo")
public class Album implements Serializable, AlbumInterface {

    @SerializedName("name")
    @Expose
    private String name;


    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("image")
    @Expose
    private List<Image> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public Artist getArtist_() {
        return null;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }


}
