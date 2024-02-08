package com.example.spotify.model.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Songs {

    @SerializedName("track")
    @Expose
    private List<Song> track;

    public List<Song> getTrack() {
        return track;
    }

    public void setTrack(List<Song> track) {
        this.track = track;
    }

}

