package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Download_Albums;
import com.example.spotify.R;

import Loading.LoadingDialog;
import modelApi.AlbumApi.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Album_Download_Adapter extends RecyclerView.Adapter<Album_Download_Adapter.ViewHolder> {


    Map<String, List<Album>> albums;
    Download_Albums context;

    private LoadingDialog loadingDialog;
    Image_Album_adapter adapter;


    public Album_Download_Adapter(Map<String, List<Album>> entrada_albums, Download_Albums context) {
        albums = entrada_albums;
        this.context = context;
        loadingDialog = new LoadingDialog(context.getContext());
    }

    @NonNull
    @Override
    public Album_Download_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_download_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Album_Download_Adapter.ViewHolder holder, int position) {



        //Obtenim tots els artistes i els seus albums
        List<String> artists = new ArrayList<>(albums.keySet());

        //Obtenim el primer artista
        String artist= artists.get(position);

        holder.recycle.setLayoutManager(new GridLayoutManager(context.getContext(),2, LinearLayoutManager.HORIZONTAL, false));

        holder.name.setText(artist);

        if(albums.get(artist)!=null && albums.get(artist).size()>0) {
            adapter = new Image_Album_adapter(albums.get(artist), context.getContext());
            holder.recycle.setAdapter(adapter);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", artist);

                NavController nav = Navigation.findNavController(context.getView());

                nav.navigate(R.id.action_global_insideArtist, bundle);
            }
        });


        hideLoading();

    }

    @Override
    public int getItemCount() {
        if(albums!= null){
            return albums.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        RecyclerView recycle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.AlbumName);
            recycle= itemView.findViewById(R.id.RecycleAlbums);
        }
    }



    private void showLoading() {
        loadingDialog.show();
    }

    // Método para ocultar la pantalla de carga
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    // Método para iniciar la carga y manejar la pantalla de carga
    public void startLoading() {
        showLoading();

        // Simula la carga de datos con un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                hideLoading();
            }
        }, 3000); // Ajusta este valor al tiempo real que tome cargar los datos
    }


}
