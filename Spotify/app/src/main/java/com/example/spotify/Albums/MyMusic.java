package com.example.spotify.Albums;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.spotify.R;
import com.example.spotify.Songs.llista_cansons;
import com.example.spotify.databinding.FragmentMyMusicBinding;
import com.example.spotify.dialogs.delete_album_custom_dialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import com.example.spotify.Adapters.Album_Adapter;
import com.example.spotify.model.AlbumClickerListener;
import com.example.spotify.ViewModel.AlbumInfoViewModel;
import com.example.spotify.model.classes.Album;


public class MyMusic extends Fragment implements AlbumClickerListener{

    //#region Atributs

   FragmentMyMusicBinding b;

   public static Album_Adapter adapter;

   AlbumInfoViewModel viewModel;



    public ActionMode.Callback actionModeCallback;

    public static long imagenumber=0;



    //#endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity()).get(AlbumInfoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentMyMusicBinding.inflate(getLayoutInflater());
        View v = b.getRoot();






        viewModel.setLlista(Album.list_albums);


        setupUniversalImageLoader();

        setUpRecycleViewAlbums();

        setUpAnimations();

        setUpActionBar();

        setUpFloatingButton();





        return v;
    }


    //#region Metodes propis

    //Configuració i activació de la ContextualActionBar que enllacem desde el album_adapter
    public void setUpActionBar() {

        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
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

                //Comportament segons el item del menu seleccionat
                if (item.getItemId() == R.id.action_delete) {

                    confirmarEliminació();
                    mode.finish(); // Finaliza el Action Mode
                    return true;
                }else if(item.getItemId()==R.id.action_edit){

                    for (Album a : Album.list_albums) {
                        if (a.isSelected()) {
                            Album_Creation.entrada= a;

                        }
                    }

                    mode.finish();

                    Navigation.findNavController(MyMusic.this.getView()).navigate(R.id.action_global_album_Creation);


                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                adapter.notifyDataSetChanged();
            }


        };


    }


    //Elimina el album seleccionat
    public static void deleteSelectedItems() {
        List<Album> selectedItems = new ArrayList<>();

        // Itera sobre musicList para identificar elementos seleccionados
        for (Album item : Album.list_albums) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }

        // Elimina los elementos seleccionados de musicList
        Album.list_albums.removeAll(selectedItems);

        // Notifica al adaptador del cambio
        adapter.notifyDataSetChanged();
    }


    //Configuració del botó flotant
    private void setUpFloatingButton() {

        b.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_global_album_Creation);
            }
        });

    }

    //Configuració i activació de les animacions de la pantalla
    private void setUpAnimations() {
        Animation slideUp = AnimationUtils.loadAnimation(this.getContext(), R.anim.floating_button_anim);
        b.fab.startAnimation(slideUp);

    }


    //Configuració del ImageLoader per poder descarregar les imatges
    private void setupUniversalImageLoader() {
        DisplayImageOptions dop = new DisplayImageOptions.Builder().
                showImageOnLoading(R.drawable.not_found)
                .build();
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(this.getContext())
                        //.threadPoolSize(10)
                        //.diskCacheSize(1000)
                        .defaultDisplayImageOptions(dop)
                        .build();

        ImageLoader.getInstance().init(config);
    }

    //Configuració del recycleview de la pantalla
    private void setUpRecycleViewAlbums() {



        b.rcyAlbums.setLayoutManager(new GridLayoutManager(
                this.getContext(),2,
                LinearLayoutManager.VERTICAL,
                false));





        viewModel.getLlistaAlbums().observe(getViewLifecycleOwner(), albums -> {

            adapter= new Album_Adapter(albums, this,this,viewModel);
            b.rcyAlbums.setAdapter(adapter);




        });







    }


    //Confirmació de la eliminació del àlbum seleccionatt
    private void confirmarEliminació() {
        delete_album_custom_dialog customDialog = new delete_album_custom_dialog(MyMusic.this);

        customDialog.show(getFragmentManager(), "CustomDialogFragment");
    }


    @Override
    public void AlbumClicked(Album entrada) {
        llista_cansons.entrada=entrada;
        //if(MainActivity.v==null)
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            Navigation.findNavController(MyMusic.this.getView()).navigate(R.id.action_global_llista_cansons);
        } else{
            llista_cansons nova = new llista_cansons();
            nova.entrada=entrada;

            if(getActivity()!=null){
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment2,nova).commit();
            }

        }
    }





    //#endregion
}
