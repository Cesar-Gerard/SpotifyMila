package com.example.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.databinding.FragmentDownloadAlbumsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.spotify.API.LastFMManager;
import com.example.spotify.Adapters.Album_Download_Adapter;
import com.example.spotify.Adapters.Artist_Adapter;
import com.example.spotify.ViewModel.DownloadAlbumViewModel;
import com.example.spotify.ViewModel.DownloadArtistViewModel;
import com.example.spotify.model.classes.Album;
import com.example.spotify.modelApi.AlbumApi.SearchAlbum;
import com.example.spotify.modelApi.ArtistApi.Artist;
import com.example.spotify.modelApi.ArtistApi.SearchArtist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Download_Albums extends Fragment {

    FragmentDownloadAlbumsBinding binding;
    LastFMManager manager;

    DownloadAlbumViewModel viewModel;
    DownloadArtistViewModel viewModelArtist;

    Map<String, List<Album>> groupedAlbums= new HashMap<>();
    List<Artist> resultArtist= new ArrayList<>();

    public static Artist_Adapter artist_adapter;
    public static Album_Download_Adapter album_adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel= new ViewModelProvider(requireActivity()).get(DownloadAlbumViewModel.class);
        viewModelArtist = new ViewModelProvider(requireActivity()).get(DownloadArtistViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentDownloadAlbumsBinding.inflate(getLayoutInflater());

        viewModel.setLlista(groupedAlbums);
        viewModelArtist.setLlista(resultArtist);

        binding.EditTextCerca.setText("");

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

                List<Artist> resultat = response.body().getResults().getArtistmatches().getArtist();

                resultArtist=resultat;

                viewModelArtist.getLlistaArtista().observe(getViewLifecycleOwner(), artists -> {


                    hideLoadingArtist();
                    recycleArtists(resultArtist);


                });



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
                    groupedAlbums= groupedAlbumsByArtists(result);


                    viewModel.getLlistaAlbums().observe(getViewLifecycleOwner(),albums ->{

                        hideLoadingAlbum();
                        recycleAlbums(groupedAlbums);

                    });




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
            album.procesarArtist();

            String artista= album.getArtistname();

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


        album_adapter = new Album_Download_Adapter(albums, this);
        binding.RecycleDownload.setAdapter(album_adapter);


    }

    private void recycleArtists(List<Artist> artistes) {


        artist_adapter = new Artist_Adapter(artistes, this);
        binding.RecycleDownload.setAdapter(artist_adapter);





    }
    //endregion


    private void handleLoadingAlbum() {

        if (album_adapter != null) {
            album_adapter.startLoading();
        }
    }


    private void showLoadingBeforeSearchAlbum() {
        album_adapter = new Album_Download_Adapter(new HashMap<>(), this);
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
        artist_adapter = new Artist_Adapter(new ArrayList<>(), this);
        binding.RecycleDownload.setAdapter(artist_adapter);
        handleLoadingArtist();
    }

    // Método para ocultar la pantalla de carga
    private void hideLoadingArtist() {
        if (artist_adapter != null) {
            artist_adapter.hideLoading();
        }
    }



}