
package com.example.spotify.modelApi.ArtistApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Results {

    @SerializedName("artistmatches")
    @Expose
    private Artistmatches artistmatches;


    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Artistmatches getArtistmatches() {
        return artistmatches;
    }


    public void setArtistmatches(Artistmatches artistmatches) {
        this.artistmatches = artistmatches;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }



}
