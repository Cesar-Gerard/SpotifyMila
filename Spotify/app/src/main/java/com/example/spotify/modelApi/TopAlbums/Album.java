
package com.example.spotify.modelApi.TopAlbums;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.List;

import com.example.spotify.modelApi.AlbumApi.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

import com.example.spotify.Interface.AlbumInterface;

@Generated("jsonschema2pojo")
public class Album implements AlbumInterface {



    @ColumnInfo(name = "album_title")
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("image")
    @Expose
    private List<Image> image;

    public String getName() {
        return name;
    }

    @Override
    public String getArtist() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Artist getArtist_() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

}
