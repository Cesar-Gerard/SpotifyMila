package com.example.spotify.Songs;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.spotify.R;
import com.example.spotify.ViewModel.SongViewMode;
import com.example.spotify.databinding.FragmentLlistaCansonsBinding;
import com.example.spotify.dialogs.Song_Creation_CustomDialog;
import com.example.spotify.dialogs.delete_album_custom_dialog;

import java.util.ArrayList;
import java.util.List;

import com.example.spotify.Adapters.Song_Adapter;
import com.example.spotify.ViewModel.AlbumInfoViewModel;
import com.example.spotify.model.classes.Album;
import com.example.spotify.model.classes.Song;


public class llista_cansons extends Fragment  {


    //#region Variables

    static FragmentLlistaCansonsBinding b;

    Song_Creation_CustomDialog dialog;


    static AlbumInfoViewModel viewModel;
    static SongViewMode viewSong;

    public static Song_Adapter adapter;

    public static ActionMode.Callback actionModeCallback;

     public static Album entrada=null;


     //#endregion


    @Override
    public void onPause() {
        super.onPause();

        if(dialog!=null){
            dialog.dismiss();
        }


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragmentoActual = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        Fragment fill = fragmentoActual.getChildFragmentManager().getPrimaryNavigationFragment();
        String nom = fill.getClass().getSimpleName();


        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT && nom.equals("llista_cansons")){

            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_global_myMusic);
        }




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AlbumInfoViewModel.class);
        viewSong= new ViewModelProvider(requireActivity()).get(SongViewMode.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentLlistaCansonsBinding.inflate(getLayoutInflater());
        View v = b.getRoot();

        if(entrada!=null){


            LiveData<List<Song>> songs =  viewModel.getSongs(entrada.getId());
            songs.observe(getActivity(),lessongs -> {
                        Log.d("XXX", "song:"+lessongs);
                        viewSong.setLlista(lessongs);
                        entrada.setConsons_Album(viewSong.getllistaSongs().getValue());

                        setUpAlbumInfo();
                        setUpAnimations();

                        setUpRecycleViewCansons();

                        setUpFloatingButton();

                        setUpActionBar();


            }
            );







        }


        return v;
    }



    //#region Metodes propis

    //Comportament y activació de la Contextual ActionBAr que ens permet eliminar o editar la canço seleccionada
    private void setUpActionBar() {

        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Definim el menú contextual
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_action_bar_menu_album, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                //Comportament depenent del item del menu seleccionat
                if (item.getItemId() == R.id.action_delete) {

                    confirmarEliminació();
                    viewModel.setLlista(Album.list_albums);
                    mode.finish(); // Finaliza el Action Mode
                    return true;
                }else if(item.getItemId()==R.id.action_edit){

                    for (Song a : entrada.getConsons_Album()) {
                        if (a.isSelected()) {
                            Song_Creation_CustomDialog.canso= a;
                        }
                    }

                    mode.finish();

                    //Pantalla de edició de canço
                    Song_Creation_CustomDialog customDialog = new Song_Creation_CustomDialog();
                    customDialog.setLlista_Canso(llista_cansons.this);
                    customDialog.show(getFragmentManager(), "CustomDialogFragment");


                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.notifyDataSetChanged();
            }
        };
    }


    //Crea el dialeg que ens demana confirmació per la eliminació desde el ContextActioBar
    private void confirmarEliminació() {
        delete_album_custom_dialog customDialog = new delete_album_custom_dialog(llista_cansons.this);

        customDialog.show(getFragmentManager(), "CustomDialogFragment");
    }

    //Eliminació de la canço seleccionada y actualització del recyckeview
    public static void deleteSelectedItems() {
        List<Song> selectedItems = new ArrayList<>();
        int posicio = 0;


        for (Song item : entrada.getConsons_Album()) {
            if (item.isSelected()) {
                selectedItems.add(item);
                posicio = (int)item.getId();
            }
        }



        entrada.getConsons_Album().remove(selectedItems.get(selectedItems.size()-1));

        for(int i =posicio; i < entrada.getConsons_Album().size(); i++ ){
            Song j = entrada.getConsons_Album().get(i);

            entrada.getConsons_Album().get(i).setId((int) j.getId()-1);
        }

        int p = Album.list_albums.indexOf(entrada);


        Album.list_albums.get(p).setConsons_Album(entrada.getConsons_Album());
        viewModel.setLlista(Album.list_albums);

        adapter.notifyDataSetChanged();

    }


    //Configuració del comportament del boto flotant
    private void setUpFloatingButton() {

        b.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Song_Creation_CustomDialog();
                dialog.setLlista_Canso(llista_cansons.this);
                dialog.show(getActivity().getSupportFragmentManager(), "CustomDialogFragment");
            }
        });

    }


    //Configuració y activació de les animacions de la pantalla
    public void setUpAnimations() {

        Animation slideUp = AnimationUtils.loadAnimation(this.getContext(), R.anim.floating_button_anim);
        b.fab.startAnimation(slideUp);

        flipanimation();


        b.txvNumSongs.setText(String.valueOf(entrada.getConsons_Album().size()));



    }

    //Animació separada de la resta per poder fer-la servir en altres pantalles
    public void flipanimation() {
        Animation Flip = AnimationUtils.loadAnimation(this.getContext(), R.anim.flip_animation);
        b.imgcassete.startAnimation(Flip);
    }


    //Presentació de les dades del album
    public static void setUpAlbumInfo() {
        b.txvNameArtist.setText(entrada.getArtistname().toString());

        b.txvAlbumName.setText(entrada.getName().toString());

        b.imgAlbum.setImageBitmap(entrada.getImageBitmap());


    }



    //Configuració del recycleview
    public void setUpRecycleViewCansons() {


            cardAvisEnabled();

            b.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
            adapter=new Song_Adapter(entrada.getConsons_Album(),this);
            b.recyclerView.setAdapter(adapter);

    }


    //Comportament per mostrar o ocultar part dels elements del xml
    public  static void cardAvisEnabled(){


        if(entrada.getConsons_Album().size()>0){
            b.cardAvis.setVisibility(View.GONE);
            b.txvNumSongs.setText(String.valueOf(entrada.getConsons_Album().size()));
            b.recyclerView.setVisibility(View.VISIBLE);

        }else if(entrada.getConsons_Album().size()==0){
            b.cardAvis.setVisibility(View.VISIBLE);
            b.txvNumSongs.setText(String.valueOf(entrada.getConsons_Album().size()));
            b.recyclerView.setVisibility(View.GONE);
        }





    }

    //#endregion


}