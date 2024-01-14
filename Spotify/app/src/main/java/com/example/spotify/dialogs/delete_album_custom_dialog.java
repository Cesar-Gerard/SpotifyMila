package com.example.spotify.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.Albums.MyMusic;
import com.example.spotify.databinding.FragmentDeleteAlbumCustomDialogBinding;
import com.example.spotify.Songs.llista_cansons;


public class delete_album_custom_dialog extends DialogFragment {
    
    
    FragmentDeleteAlbumCustomDialogBinding b;

    Object entrada;

    public delete_album_custom_dialog(Object o) {
        entrada = o;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentDeleteAlbumCustomDialogBinding.inflate(getLayoutInflater());
        View v = b.getRoot();

        if(entrada instanceof llista_cansons){
            b.textView.setText("Segur que vols eliminar la canço seleccionada?");
        }

        setUpButtons();
        

        return v;
        
    }

    private void setUpButtons() {

        b.btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(entrada instanceof MyMusic){
                    MyMusic.deleteSelectedItems();
                }else if(entrada instanceof  llista_cansons){
                    llista_cansons.deleteSelectedItems();
                    llista_cansons.cardAvisEnabled();
                }

                delete_album_custom_dialog.this.dismiss();

            }
        });


        b.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_album_custom_dialog.this.dismiss();
            }
        });

    }
}