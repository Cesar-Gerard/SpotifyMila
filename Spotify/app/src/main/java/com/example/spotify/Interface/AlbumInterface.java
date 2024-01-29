package com.example.spotify.Interface;

import java.util.List;

import com.example.spotify.modelApi.AlbumApi.Image;
import com.example.spotify.modelApi.TopAlbums.Artist;

public interface AlbumInterface {

    String getName();
    String getArtist();

    Artist getArtist_();
    List<Image> getImage();

}
