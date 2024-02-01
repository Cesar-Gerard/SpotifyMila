
package com.example.spotify.modelApi.AlbumApi;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;


@Generated("jsonschema2pojo")
public class Album implements Serializable {


    private String artistname;




    @SerializedName("name")
    @Expose
    private String name;




    private Bitmap imageBitmap;


    @SerializedName("artist")
    @Expose
    private Object artist;



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
        return artistname;
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


    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void procesarArtist() {
        if (artist instanceof String) {
            artistname = (String) artist;
        }
    }

}
