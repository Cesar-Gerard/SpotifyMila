
package com.example.spotify.modelApi.InfoApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class InfoArtist {

    @SerializedName("artist")
    @Expose
    private Artist_Info artistInfo;

    public Artist_Info getArtist() {
        return artistInfo;
    }

    public void setArtist(Artist_Info artistInfo) {
        this.artistInfo = artistInfo;
    }

}
