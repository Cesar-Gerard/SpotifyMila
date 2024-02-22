package com.example.spotify.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.ViewModel.SongViewMode;
import com.example.spotify.databinding.FragmentSongCreationCustomDialogBinding;
import com.example.spotify.Songs.llista_cansons;

import java.util.List;

import com.example.spotify.ViewModel.AlbumInfoViewModel;
import com.example.spotify.model.classes.Album;
import com.example.spotify.model.formatters.FormatTime;
import com.example.spotify.model.classes.Song;


public class Song_Creation_CustomDialog extends DialogFragment {


    FragmentSongCreationCustomDialogBinding b;


    llista_cansons llista;

    public static Song canso;

    AlbumInfoViewModel viewModel;

    SongViewMode viewSong;

    private Handler handler = new Handler();
    private Runnable inputFinishChecker;




    @Override
    public void onStart() {
        super.onStart();

        // Ajustar el tamaño del diálogo
        if (getDialog() != null) {
            // Establecer un ancho y alto específicos
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AlbumInfoViewModel.class);
        viewSong=new ViewModelProvider(requireActivity()).get(SongViewMode.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        b = FragmentSongCreationCustomDialogBinding.inflate(getLayoutInflater());
        View v = b.getRoot();


        setUpConfirmButton();
        setUpCancelButton();
        setUpControllInfo();

        setUpNumberPicker();






        if(canso!=null){
            b.pickerid.setEnabled(true);
            setUpSong();
        }else{
            b.pickerid.setValue(llista_cansons.entrada.getNewSongPosition());
            b.pickerid.setEnabled(false);
        }



        return v;

    }

    private void setUpSong() {

        b.edtNameSong.setText(canso.getName());



        b.numberPickerMinutos.setValue(canso.convertToMinutes(canso.getTime()));
        b.numberPickerSegundos.setValue(canso.convertToSeconds(canso.getTime())-3);
        b.pickerid.setValue(canso.getPosicio());

    }

    private void setUpControllInfo() {

            b.edtNameSong.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {





                }

                @Override
                public void afterTextChanged(Editable s) {

                    handler.removeCallbacks(inputFinishChecker);


                    inputFinishChecker = new Runnable() {
                        @Override
                        public void run() {
                            // Verifica el texto después del retraso
                            if(!Album.songInAlbumRealesed(b.edtNameSong.getText().toString(),llista_cansons.entrada, canso)){
                                b.btnSave.setEnabled(true);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Nom ja registrat")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                b.edtNameSong.setText("");
                                                b.btnSave.setEnabled(false);
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    };

                    // Inicia el temporizador después de 1000 ms (1 segundo)
                    handler.postDelayed(inputFinishChecker, 400);





                }
            });

    }

    private void setUpCancelButton() {
        b.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song_Creation_CustomDialog.this.dismiss();
                llista.flipanimation();
            }
        });
    }

    private void setUpConfirmButton() {
        b.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (canso == null) {


                    String name = b.edtNameSong.getText().toString();
                    boolean fav = false;

                    String time = FormatTime.formatIntToString(b.numberPickerMinutos.getValue()) + ":" + FormatTime.formatIntToString(b.numberPickerSegundos.getValue());

                    int position =b.pickerid.getValue();

                    Song nou = new Song(viewSong.getAlbumId(), fav, name, time,position);

                    viewModel.insertSong(nou);

                    llista_cansons.adapter.notifyDataSetChanged();


                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                        actualitzarllistaAlbums(llista_cansons.entrada);
                        viewModel.setLlista(Album.list_albums);

                    }

                    llista_cansons.cardAvisEnabled();
                }else{



                    llista_cansons.entrada.getConsons_Album().get(canso.getPosicio()).setName(b.edtNameSong.getText().toString());
                    llista_cansons.entrada.getConsons_Album().get(canso.getPosicio()).setSelected(false);

                    String time = FormatTime.formatIntToString(b.numberPickerMinutos.getValue()) + ":" + FormatTime.formatIntToString(b.numberPickerSegundos.getValue());

                    llista_cansons.entrada.getConsons_Album().get(canso.getPosicio()).setTime(time);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Canço actualitzada")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    b.edtNameSong.setText("");
                                    b.numberPickerSegundos.setValue(0);
                                    b.numberPickerMinutos.setValue(0);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();



                    int posicionSelected = b.pickerid.getValue();
                    if(posicionSelected!= canso.getPosicio()){
                        intercambiarCancionEnLista(posicionSelected,canso);
                    }

                    viewModel.updateSong(canso);

                    canso= null;

                    llista_cansons.adapter.notifyDataSetChanged();


                }
                Song_Creation_CustomDialog.this.dismiss();

                llista.flipanimation();




            }
        });
    }

    private void actualitzarllistaAlbums(Album entrada) {

        int posicio = Album.list_albums.indexOf(entrada);

        Album.list_albums.get(posicio).setConsons_Album(entrada.getConsons_Album());
        Album.list_albums.get(posicio).setImageBitmap(entrada.getImageBitmap());
        Album.list_albums.get(posicio).setId(entrada.getId());
        Album.list_albums.get(posicio).setName(entrada.getName());
        Album.list_albums.get(posicio).setArtistname(entrada.getArtistname());
        Album.list_albums.get(posicio).setDate(entrada.getDate());
        Album.list_albums.get(posicio).setImageUrl(entrada.getImageUrl());
        Album.list_albums.get(posicio).setSelected(entrada.isSelected());




    }


    public void setLlista_Canso(llista_cansons llista){

        this.llista= llista;

    }


    private void setUpNumberPicker() {

        b.numberPickerMinutos.setMaxValue(59);
        b.numberPickerMinutos.setMinValue(0);
        b.numberPickerMinutos.setValue(0);

        b.numberPickerSegundos.setMaxValue(59);
        b.numberPickerSegundos.setMinValue(0);
        b.numberPickerSegundos.setValue(0);

        b.pickerid.setMinValue(0);

        int max = llista_cansons.entrada.getConsons_Album().size();
        if(max >0){
            if(canso==null) {
                b.pickerid.setMaxValue(max);
                b.pickerid.setValue(max);
            }else{
                b.pickerid.setMaxValue(llista_cansons.entrada.getConsons_Album().size()-1);
            }

        }else{
            b.pickerid.setMaxValue(0);
        }




    }


    private void intercambiarCancionEnLista(int idSeleccionado,Song cancionModificada) {
        List<Song> cansons= llista_cansons.entrada.getConsons_Album();

        for (Song song : cansons) {
            if (song.getPosicio() == idSeleccionado) {
                // Intercambiar la posición de la canción modificada con la encontrada en la lista
                int indiceModificada = cansons.indexOf(cancionModificada);
                int indiceEncontrada = cansons.indexOf(song);

                // Realizar el intercambio solo si la posición es diferente
                if (indiceModificada != indiceEncontrada) {
                    cansons.set(indiceModificada, song);
                    cansons.set(indiceEncontrada, cancionModificada);


                    song.setPosicio(indiceModificada);
                    cancionModificada.setPosicio(indiceEncontrada);

                    viewModel.updateSong(song);
                    viewModel.updateSong(cancionModificada);

                }
                break;
            }
        }



        llista_cansons.entrada.setConsons_Album(cansons);
    }




}