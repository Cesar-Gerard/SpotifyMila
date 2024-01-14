package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.List;

import modelApi.ArtistApi.Artist;

public class Artist_Adapter extends RecyclerView.Adapter<Artist_Adapter.ViewHolder> {


    List<Artist> artistes;
    Context context;


    //region Constructor
    public Artist_Adapter(List<Artist> entrada_artistes, Context context) {
        artistes= entrada_artistes;
        this.context= context;

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


        holder.image.setImageResource(R.drawable.loading);
        ImageLoader iL= ImageLoader.getInstance();

        iL.loadImage(item.getImage().get(1).toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.image.setImageResource(R.drawable.not_found);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.image.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

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
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ArtistName);
            image= itemView.findViewById(R.id.ArtistImage);
        }
    }
}
