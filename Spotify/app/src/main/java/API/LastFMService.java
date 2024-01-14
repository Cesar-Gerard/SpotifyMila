package API;

import modelApi.SearchArtist;
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


}
