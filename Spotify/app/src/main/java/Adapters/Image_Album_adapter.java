package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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

import Interface.AlbumInterface;
import modelApi.AlbumApi.Album;

public class Image_Album_adapter extends RecyclerView.Adapter<Image_Album_adapter.ViewHolder>{

    List<Album> albums;
    List<modelApi.TopAlbums.Album> top;
    Context context;

    public Image_Album_adapter(List<Album> albums,Context context) {
        this.albums=albums;
        this.context=context;
    }

    public Image_Album_adapter(List<modelApi.TopAlbums.Album> album, Context context,boolean isTop) {
        top=album;
        this.context=context;

    }

    @NonNull
    @Override
    public Image_Album_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_image_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_Album_adapter.ViewHolder holder, int position) {

        if(albums!=null) {


            Album item = albums.get(position);


            CarregarImatge(holder, position, item, 120);
        }else if(top != null){

            modelApi.TopAlbums.Album itemTop = top.get(position);

            CarregarImatge(holder, position, itemTop, 150);


        }


    }

    private void CarregarImatge(@NonNull ViewHolder holder, int position, AlbumInterface item, int escala) {
        if (item.getImage().get(1).getImageBitmap() != null) {

            if (holder.getAdapterPosition() == position) {
                holder.imagen.setImageBitmap(item.getImage().get(1).getImageBitmap());
            }


        } else {


            if (item.getImage().get(3).getText().equals("")) {


                int resourceId = R.drawable.music_not_found;

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

                float scale = context.getResources().getDisplayMetrics().density;

                int widthInPx = (int) (escala * scale);
                int heightInPx = (int) (escala * scale);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, widthInPx, heightInPx, true);
                item.getImage().get(1).setImageBitmap(scaledBitmap);

                holder.imagen.setImageBitmap(scaledBitmap);


            } else {


                ImageLoader iL = ImageLoader.getInstance();

                iL.loadImage(item.getImage().get(3).getText(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        int resourceId = R.drawable.music_not_found;

                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

                        float scale = context.getResources().getDisplayMetrics().density;

                        int widthInPx = (int) (escala * scale);
                        int heightInPx = (int) (escala * scale);

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, widthInPx, heightInPx, true);
                        item.getImage().get(1).setImageBitmap(scaledBitmap);

                        holder.imagen.setImageBitmap(scaledBitmap);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        float scale = context.getResources().getDisplayMetrics().density;

                        int widthInPx = (int) (escala * scale);
                        int heightInPx = (int) (escala * scale);

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(loadedImage, widthInPx, heightInPx, true);
                        item.getImage().get(1).setImageBitmap(scaledBitmap);

                        holder.imagen.setImageBitmap(scaledBitmap);


                        item.getImage().get(1).setImageBitmap(scaledBitmap);


                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e("Imatge cancelada", item.getName());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
       if(albums!= null){
           return albums.size();
       }else if(top!=null){
           return top.size();
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
