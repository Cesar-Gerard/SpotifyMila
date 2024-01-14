package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.R;

import modelApi.AlbumApi.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Album_Download_Adapter extends RecyclerView.Adapter<Album_Download_Adapter.ViewHolder> {


    Map<String, List<Album>> albums;
    Context context;

    Image_Album_adapter adapter;

    public Album_Download_Adapter(Map<String, List<Album>> entrada_albums, Context context) {
        albums = entrada_albums;
        this.context= context;
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

        holder.recycle.setLayoutManager(new GridLayoutManager(context,2, LinearLayoutManager.HORIZONTAL, false));
        adapter= new Image_Album_adapter(albums.get(artist));
        holder.recycle.setAdapter(adapter);


        holder.name.setText(artist);

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
}
