package Adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import modelApi.AlbumApi.Album;

public class Image_Album_adapter extends RecyclerView.Adapter<Image_Album_adapter.ViewHolder>{

    List<Album> albums;

    public Image_Album_adapter(List<Album> albums) {
        this.albums=albums;
    }

    @NonNull
    @Override
    public Image_Album_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_image_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_Album_adapter.ViewHolder holder, int position) {

        Album item = albums.get(position);



        ImageLoader iL= ImageLoader.getInstance();

        iL.loadImage(item.getImage().get(1).toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.imagen.setImageResource(R.drawable.not_found);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.imagen.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });


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

        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imagen = itemView.findViewById(R.id.ImageAlbum);
        }
    }
}
