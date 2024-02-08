package com.example.spotify.API;

import com.example.spotify.model.classes.Album;
import com.example.spotify.modelApi.AlbumApi.SearchAlbum;
import com.example.spotify.modelApi.ArtistApi.SearchArtist;
import com.example.spotify.modelApi.InfoApi.InfoArtist;
import com.example.spotify.modelApi.SongsAlbum.SongsAlbum;
import com.example.spotify.modelApi.TopAlbums.SearchTopAlbums;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMService {

    @GET("/2.0/")
    Call<SearchArtist> searchArtists(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("api_key") String apiKey,
            @Query("format") String format
    );


    @GET("/2.0/")
    Call<SearchAlbum> searchAlbum(
            @Query("method") String method,
            @Query("album") String album,
            @Query("api_key") String apiKey,
            @Query("format") String format
    );


    @GET("/2.0/")
    Call<SearchTopAlbums> searchTopAlbums(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("api_key") String apiKey,
            @Query("format") String format
    );


    @GET("/2.0/")
    Call<InfoArtist> getInfoArtist(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("api_key") String apiKey,
            @Query("format") String format
    );

    @GET("/2.0/")
    Call<SongsAlbum> getSongs(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("artist") String artist,
            @Query("album") String album,
            @Query("format") String format
    );


}
