package com.example.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.ViewModel.InsideAlbumViewModel;
import com.example.spotify.databinding.ArtistContentBinding;

import com.example.spotify.API.LastFMManager;
import com.example.spotify.Adapters.Image_Album_adapter;
import com.example.spotify.model.classes.Album;
import com.example.spotify.modelApi.InfoApi.InfoArtist;
import com.example.spotify.modelApi.TopAlbums.SearchTopAlbums;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsideArtist extends Fragment {


    ArtistContentBinding binding;

    Image_Album_adapter adapter;

    InsideAlbumViewModel viewModel;


    List<Album> albums= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel= new ViewModelProvider(requireActivity()).get(InsideAlbumViewModel.class);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=ArtistContentBinding.inflate(getLayoutInflater());

        viewModel.setLlista(albums);


        customButton();

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtener el parámetro String
            String parametro = bundle.getString("album");

            getTopAlbums(parametro);
            getArtistInfo(parametro);


            binding.TitleName.setText(parametro);



        }


        View v= binding.getRoot();
        return v;
    }

    private void customButton() {

        binding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }


    //Obtenim la informació del Artista
    private void getArtistInfo(String parametro) {
        LastFMManager.getInstance().getInfoArtist(parametro, new Callback<InfoArtist>() {
            @Override
            public void onResponse(Call<InfoArtist> call, Response<InfoArtist> response) {

                if(response.body().getArtist().getBio().getContent().toString().equals("") || response.body().getArtist().getBio().getContent().toString().equals(null)){
                    binding.InfoArtist.setText("No Description Avaliable");
                }else{
                    binding.InfoArtist.setText(response.body().getArtist().getBio().getContent().toString());
                }


            }

            @Override
            public void onFailure(Call<InfoArtist> call, Throwable t) {

            }
        });
    }

    //Obtenim els albums ordenats per rellevança
    private void getTopAlbums(String parametro) {
        showLoadingBeforeSearch();
        LastFMManager.getInstance().getTopAlbums(parametro, new Callback<SearchTopAlbums>() {
            @Override
            public void onResponse(Call<SearchTopAlbums> call, Response<SearchTopAlbums> response) {

                albums = response.body().getTopalbums().getAlbum();

                viewModel.getLlistaAlbums().observe(getViewLifecycleOwner(), albums1 -> {



                    hideLoading();

                    adapter = new Image_Album_adapter(albums,InsideArtist.this.getContext(),true);
                    binding.LlistaAlbums.setLayoutManager(new GridLayoutManager(InsideArtist.this.getContext(),2, LinearLayoutManager.VERTICAL, false));
                    binding.LlistaAlbums.setAdapter(adapter);




                });



            }

            @Override
            public void onFailure(Call<SearchTopAlbums> call, Throwable t) {
                Log.e("Error getTopAlbumsArtist()", t.getLocalizedMessage());
            }
        });
    }





    private void handleLoading() {

        if (adapter != null) {
            adapter.startLoading();
        }

    }


    private void showLoadingBeforeSearch() {
        adapter = new Image_Album_adapter(new ArrayList<>(), InsideArtist.this.getContext(),true);
        binding.LlistaAlbums.setAdapter(adapter);
        handleLoading();
    }

    // Método para ocultar la pantalla de carga
    private void hideLoading() {
        if (adapter != null) {
            adapter.hideLoading();
        }
    }


}