package com.example.spotify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify.ViewModel.AlbumInfoViewModel;
import com.example.spotify.ViewModel.InsideAlbumViewModel;
import com.example.spotify.databinding.ArtistContentBinding;

import com.example.spotify.API.LastFMManager;
import com.example.spotify.Adapters.Image_Album_adapter;
import com.example.spotify.dialogs.download_album_customDialog;
import com.example.spotify.model.classes.Album;
import com.example.spotify.model.classes.Song;
import com.example.spotify.model.formatters.BitmapUtils;
import com.example.spotify.modelApi.InfoApi.InfoArtist;
import com.example.spotify.modelApi.SongsAlbum.SongsAlbum;
import com.example.spotify.modelApi.TopAlbums.SearchTopAlbums;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsideArtist extends Fragment {


    ArtistContentBinding binding;

    Image_Album_adapter adapter;

    InsideAlbumViewModel viewModel;

    static Album descarregat;

    private static long id_album;

    static AlbumInfoViewModel viewAlbum;

    public ActionMode.Callback actionModeCallback;



    static List<Album> albums= new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel= new ViewModelProvider(requireActivity()).get(InsideAlbumViewModel.class);
        viewAlbum= new ViewModelProvider(requireActivity()).get(AlbumInfoViewModel.class);

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
            setUpActionBar();


            binding.TitleName.setText(parametro);



        }


        View v= binding.getRoot();
        return v;
    }



    public void setUpActionBar() {

        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_download_album, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                //Comportament segons el item del menu seleccionat
                if (item.getItemId() == R.id.action_download) {

                    confirmarDownload();
                    mode.finish(); // Finaliza el Action Mode
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                //adapter.notifyDataSetChanged();
            }


        };


    }

    private void confirmarDownload() {

        download_album_customDialog customDialog = new download_album_customDialog(InsideArtist.this);

        customDialog.show(getFragmentManager(), "CustomDialogFragment");

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

                    adapter = new Image_Album_adapter(albums,InsideArtist.this,InsideArtist.this.getContext(),true);
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


    public static void downloadItemSelected(Context context) {
        // Itera sobre musicList para identificar elementos seleccionados
        for (Album item : albums) {
            if (item.isSelected()) {
                    item.setDownload(true);
                    item.setDate(null);
                    item.setImageBitmap(item.getImage().get(1).getImageBitmap());
                    File guardar = BitmapUtils.saveBitmapToGallery(item.getImageBitmap(), item);
                    if (guardar != null) {
                        item.setImagepath(BitmapUtils.getFilePath(guardar));
                    }

                    viewAlbum.insert(item).observeOn(Schedulers.io())
                            .subscribe(id -> {
                                id_album=id;

                            }, throwable -> {
                                Log.e("Error", "Error al insertar el album", throwable);
                            });
                    item.setSelected(false);
                    descarregat=item;



            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Album descarregat")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();




    }


    private void handleLoading() {

        if (adapter != null) {
            adapter.startLoading();
        }

    }


    private void showLoadingBeforeSearch() {
        adapter = new Image_Album_adapter(new ArrayList<>(), InsideArtist.this,InsideArtist.this.getContext(),true);
        binding.LlistaAlbums.setAdapter(adapter);
        handleLoading();
    }

    // Método para ocultar la pantalla de carga
    private void hideLoading() {
        if (adapter != null) {
            adapter.hideLoading();
        }
    }


    public static void downloadSongAlbum() {

        LastFMManager.getInstance().getSongs(descarregat.getArtistname(), descarregat.getName(), new Callback<SongsAlbum>() {
            @Override
            public void onResponse(Call<SongsAlbum> call, Response<SongsAlbum> response) {

                if(response.body()!=null) {

                    if(response.body().getAlbum().getLlista_cansons().getTrack()!=null) {

                        int posicio = 0;


                        for (Song canso : response.body().getAlbum().getLlista_cansons().getTrack()) {
                            canso.setAlbum_id(id_album);
                            canso.setPosicio(posicio);
                            canso.convertTime(canso.getTime_original());
                            viewAlbum.insertSong(canso);
                            posicio++;
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<SongsAlbum> call, Throwable t) {

            }
        });

    }


}