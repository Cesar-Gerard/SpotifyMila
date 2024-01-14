package com.example.spotify.dialogs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.R;
import com.example.spotify.databinding.FragmentDownloadAlbumsBinding;

import API.LastFMManager;
import modelApi.SearchArtist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Download_Albums extends Fragment {

    FragmentDownloadAlbumsBinding binding;
    LastFMManager manager;


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


        //Proba de cerca
        provaApi();


        View v = binding.getRoot();
        return v;
    }

    private void provaApi() {

        manager.getArtista("Adele", new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {

                Log.e("Prova",response.body().getResults().getArtistmatches().getArtist().get(0).getName().toString());


            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {

            }
        });


    }
}