package API;

import modelApi.AlbumApi.SearchAlbum;
import modelApi.ArtistApi.SearchArtist;
import modelApi.InfoApi.InfoArtist;
import modelApi.TopAlbums.SearchTopAlbums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastFMManager {

    private static final String BASE_URL = "http://ws.audioscrobbler.com/";

    private static final String TOKEN= "65d3e67caf56d1f189dba5ceaffa3855";
    private static final String FORMAT="json";

    private static LastFMManager mInstance;
    private LastFMService mApiService;


    private LastFMManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService=retrofit.create(LastFMService.class);
    }

    public static synchronized LastFMManager getInstance(){
        if(mInstance == null){
            mInstance = new LastFMManager();
        }

        return mInstance;
    }



    //Retorna la llista d'artistes que coincideixen amb el string pasat ordenats per ordre d'importancia
    public void getArtista(String artistname, Callback<SearchArtist> callback ){
        Call<SearchArtist> call = mApiService.searchArtists("artist.search",artistname,TOKEN,FORMAT);
        call.enqueue(callback);
    }

    public void getAlbums (String album, Callback<SearchAlbum> callback){
        Call<SearchAlbum> call = mApiService.searchAlbum("album.search",album, TOKEN, FORMAT);
        call.enqueue(callback);
    }

    public void getTopAlbums (String artista, Callback<SearchTopAlbums> callback){
        Call<SearchTopAlbums> call = mApiService.searchTopAlbums("artist.gettopalbums",artista, TOKEN, FORMAT);
        call.enqueue(callback);
    }


    public void getInfoArtist (String artista, Callback<InfoArtist> callback){
        Call<InfoArtist> call = mApiService.getInfoArtist("artist.getinfo",artista, TOKEN, FORMAT);
        call.enqueue(callback);
    }


}
