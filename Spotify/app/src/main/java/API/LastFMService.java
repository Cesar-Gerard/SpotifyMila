package API;

import modelApi.AlbumApi.SearchAlbum;
import modelApi.ArtistApi.SearchArtist;
import modelApi.TopAlbums.SearchTopAlbums;
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


}
