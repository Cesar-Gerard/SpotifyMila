package com.example.spotify.Adapters;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Download_Albums;
import com.example.spotify.R;


import java.util.List;

import com.example.spotify.Loading.LoadingArtist;
import com.example.spotify.modelApi.ArtistApi.Artist;

public class Artist_Adapter extends RecyclerView.Adapter<Artist_Adapter.ViewHolder> {


    List<Artist> artistes;
    Download_Albums context;

    private LoadingArtist loadingDialog;


    //region Constructor
    public Artist_Adapter(List<Artist> entrada_artistes, Download_Albums context) {
        artistes = entrada_artistes;
        this.context = context;
        loadingDialog = new LoadingArtist(context.getContext());
    }

    //endregion



    @NonNull
    @Override
    public Artist_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Artist_Adapter.ViewHolder holder, int position) {
        Artist item = artistes.get(position);


        holder.name.setText(item.getName());
        holder.listeners.setText(item.getListeners());




        //Anem a mostrar els albums del artista

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Crear un Bundle para pasar el objeto Album
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", item.getName());

                NavController nav = Navigation.findNavController(context.getView());

                nav.navigate(R.id.action_download_Albums_to_insideArtist, bundle);

            }
        });




    }

    @Override
    public int getItemCount() {

        if(artistes!= null){
            return artistes.size();
        }else{
            return 0;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView listeners;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ArtistName);
            listeners = itemView.findViewById(R.id.txvListeners);
        }
    }

    // Método para mostrar la pantalla de carga
    public void showLoading() {
        loadingDialog.show();
    }

    // Método para ocultar la pantalla de carga
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    public void startLoading() {
        showLoading();

        // Simula la carga de datos con un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                hideLoading();
            }
        }, 0);
    }

}
