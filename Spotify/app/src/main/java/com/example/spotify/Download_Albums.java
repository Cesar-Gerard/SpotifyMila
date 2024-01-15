package com.example.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.databinding.FragmentDownloadAlbumsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import API.LastFMManager;
import Adapters.Album_Download_Adapter;
import Adapters.Artist_Adapter;
import modelApi.AlbumApi.Album;
import modelApi.AlbumApi.SearchAlbum;
import modelApi.ArtistApi.Artist;
import modelApi.ArtistApi.SearchArtist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Download_Albums extends Fragment {

    FragmentDownloadAlbumsBinding binding;
    LastFMManager manager;

    public static Artist_Adapter artist_adapter;
    public static Album_Download_Adapter album_adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentDownloadAlbumsBinding.inflate(getLayoutInflater());

        //Preparem la api
        manager = LastFMManager.getInstance();

        //Configuracio general del RecycleView
        binding.RecycleDownload.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL, false));




        setUpSearch();




        View v = binding.getRoot();
        return v;
    }





    //region Cerca de Album i Artista


    private void setUpSearch() {

        binding.SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId == R.id.radioArtists){
                    ArtistSearch();
                } else if (selectedRadioButtonId == R.id.radioAlbums){
                    AlbumSearch();
                }
            }
        });

    }


    private void ArtistSearch() {
        showLoadingBeforeSearchArtist();
        manager.getArtista(binding.EditTextCerca.getText().toString(), new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {

                List<Artist> result = response.body().getResults().getArtistmatches().getArtist();
                recycleArtists(result);

                hideLoadingArtist();

            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {
                hideLoadingArtist();
            }
        });
    }


    private void AlbumSearch() {

        showLoadingBeforeSearchAlbum();
        manager.getAlbums(binding.EditTextCerca.getText().toString(), new Callback<SearchAlbum>() {
            @Override
            public void onResponse(Call<SearchAlbum> call, Response<SearchAlbum> response) {

                List<Album> result = response.body().getResults().getAlbummatches().getAlbum();

                if(result!=null && !result.isEmpty()){
                    Map<String, List<Album>> groupedAlbums= groupedAlbumsByArtists(result);
                    recycleAlbums(groupedAlbums);
                }





            }

            @Override
            public void onFailure(Call<SearchAlbum> call, Throwable t) {
                hideLoadingAlbum();
            }
        });
    }

    private Map<String, List<Album>> groupedAlbumsByArtists(List<Album> albums) {
        Map<String, List<Album>> groupedAlbums = new HashMap<>();

        for(Album album: albums){

            String artista= album.getArtist().toString();

            if(groupedAlbums.containsKey(artista)){
                groupedAlbums.get(artista).add(album);
            }else{
                List<Album> artistAlbums = new ArrayList<>();
                artistAlbums.add(album);
                groupedAlbums.put(artista, artistAlbums);
            }

        }

        return groupedAlbums;

    }

    //endregion


    //region RecycleView Gestio
    private void recycleAlbums(Map<String, List<Album>> albums) {


        album_adapter = new Album_Download_Adapter(albums, this.getContext());
        binding.RecycleDownload.setAdapter(album_adapter);


    }

    private void recycleArtists(List<Artist> artistes) {


        artist_adapter = new Artist_Adapter(artistes, this.getContext());
        binding.RecycleDownload.setAdapter(artist_adapter);


    }
    //endregion


    private void handleLoadingAlbum() {

        if (album_adapter != null) {
            album_adapter.startLoading();
        }
    }


    private void showLoadingBeforeSearchAlbum() {
        album_adapter = new Album_Download_Adapter(new HashMap<>(), getContext());
        binding.RecycleDownload.setAdapter(album_adapter);
        handleLoadingAlbum();
    }

    // Método para ocultar la pantalla de carga
    private void hideLoadingAlbum() {
        if (album_adapter != null) {
            album_adapter.hideLoading();
        }
    }








    private void handleLoadingArtist() {

        if (artist_adapter != null) {
            artist_adapter.startLoading();
        }

    }


    private void showLoadingBeforeSearchArtist() {
        artist_adapter = new Artist_Adapter(new ArrayList<>(), getContext());
        binding.RecycleDownload.setAdapter(artist_adapter);
        handleLoadingArtist();
    }

    // Método para ocultar la pantalla de carga
    private void hideLoadingArtist() {
        if (album_adapter != null) {
            album_adapter.hideLoading();
        }
    }



}