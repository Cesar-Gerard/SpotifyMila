package com.example.spotify.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.spotify.Albums.MyMusic;
import com.example.spotify.InsideArtist;
import com.example.spotify.Songs.llista_cansons;
import com.example.spotify.databinding.FragmentDeleteAlbumCustomDialogBinding;
import com.example.spotify.databinding.FragmentDownloadAlbumCustomDialogBinding;

public class download_album_customDialog extends DialogFragment {

    FragmentDownloadAlbumCustomDialogBinding b;

    Object entrada;

    public download_album_customDialog(Object o) {
        entrada = o;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentDownloadAlbumCustomDialogBinding.inflate(getLayoutInflater());
        View v = b.getRoot();



        setUpButtons();


        return v;

    }

    private void setUpButtons() {

        b.btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(entrada instanceof InsideArtist){
                    InsideArtist.downloadItemSelected(download_album_customDialog.this.getContext());
                    InsideArtist.downloadSongAlbum();
                }
                download_album_customDialog.this.dismiss();

            }
        });


        b.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download_album_customDialog.this.dismiss();
            }
        });

    }

}
