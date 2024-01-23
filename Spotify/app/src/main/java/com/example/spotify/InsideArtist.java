package com.example.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.databinding.FragmentInsideArtistBinding;

import API.LastFMManager;
import modelApi.TopAlbums.SearchTopAlbums;
import modelApi.TopAlbums.Topalbums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsideArtist extends Fragment {


    FragmentInsideArtistBinding binding;
    Topalbums resultat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentInsideArtistBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtener el parámetro String
            String parametro = bundle.getString("album");

            LastFMManager.getInstance().getTopAlbums(parametro, new Callback<SearchTopAlbums>() {
                @Override
                public void onResponse(Call<SearchTopAlbums> call, Response<SearchTopAlbums> response) {

                    resultat = response.body().getTopalbums();

                }

                @Override
                public void onFailure(Call<SearchTopAlbums> call, Throwable t) {
                    Log.e("XXX", t.getLocalizedMessage());
                }
            });




        }






        View v= binding.getRoot();
        return v;
    }
}